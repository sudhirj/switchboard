import com.google.common.collect.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImmutableBoard<S extends Supply<D>, D extends Demand> implements Board<Supply<D>, D> {

    private final ImmutableSet<S> supplies;
    private final ImmutableSet<D> demands;
    private final Table<Supply<D>, D, Choice<Supply<D>, D>> matrix;
    private final List<Choice<Supply<D>, D>> choices;

    ImmutableBoard(Collection<S> supplies, Collection<D> demands) {
        this(supplies, demands, ImmutableList.of());
    }

    private ImmutableBoard(Collection<S> supplies, Collection<D> demands, List<Choice<Supply<D>, D>> choices) {
        this.supplies = ImmutableSet.copyOf(supplies);
        this.demands = ImmutableSet.copyOf(demands);
        this.matrix = buildMatrix();
        this.choices = ImmutableList.copyOf(choices);
    }

    private ImmutableTable<Supply<D>, D, Choice<Supply<D>, D>> buildMatrix() {
        ImmutableTable.Builder<Supply<D>, D, Choice<Supply<D>, D>> builder = ImmutableTable.builder();
        for (S supply : supplies) {
            for (D demand : demands) {
                Choice<Supply<D>, D> estimate = supply.estimateFor(demand, null);
                if (estimate != null) {
                    builder.put(supply, demand, estimate);
                }
            }
        }
        return builder.build();
    }

    @Override
    public Board<Supply<D>, D> choose(Choice<Supply<D>, D> choice) {
        ImmutableList.Builder<Choice<Supply<D>, D>> newChoices = ImmutableList.builder();
        newChoices.addAll(this.choices);
        newChoices.add(choice);
        return new ImmutableBoard<>(this.supplies, this.demands, newChoices.build());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Collection<Choice<Supply<D>, D>> availableChoices() {
        return matrix.values();
    }

    @Override
    public Collection<D> unmetDemands() {
        List<D> metDemands = choices.stream().map(Choice::demand).collect(Collectors.toList());
        return Sets.filter(demands, demand -> !metDemands.contains(demand));
    }

    @Override
    public Table<Supply<D>, D, Choice<Supply<D>, D>> matrix() {
        return matrix;
    }

    @Override
    public List<Board<Supply<D>, D>> history() {
        return null;
    }

    @Override
    public List<Choice<Supply<D>, D>> choicesMade() {
        return this.choices;
    }
}
