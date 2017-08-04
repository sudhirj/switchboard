import com.google.common.collect.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImmutableBoard<S extends Supply<D>, D extends Demand> implements Board<Supply<D>, D> {

    private final ImmutableSet<S> supplies;
    private final ImmutableSet<D> demands;
    private final Table<Supply<D>, D, Choice<Supply<D>, D>> matrix;
    private final List<Choice<Supply<D>, D>> choicesMade;

    ImmutableBoard(Collection<S> supplies, Collection<D> demands) {
        this(supplies, demands, ImmutableList.of());
    }

    private ImmutableBoard(Collection<S> supplies, Collection<D> demands, List<Choice<Supply<D>, D>> choicesMade) {
        this.supplies = ImmutableSet.copyOf(supplies);
        this.demands = ImmutableSet.copyOf(demands);
        this.matrix = buildMatrix();
        this.choicesMade = ImmutableList.copyOf(choicesMade);
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
        newChoices.addAll(this.choicesMade);
        newChoices.add(choice);
        return new ImmutableBoard<>(this.supplies, this.demands, newChoices.build());
    }

    @Override
    public boolean isComplete() {
        return unmetDemands().isEmpty();
    }

    @Override
    public boolean canProceed() {
        return !this.availableChoices().isEmpty();
    }

    @Override
    public Collection<Choice<Supply<D>, D>> availableChoices() {
        return Sets.difference(ImmutableSet.copyOf(matrix.values()), ImmutableSet.copyOf(this.choicesMade));
    }

    @Override
    public Collection<D> unmetDemands() {
        return Sets.difference(demands, choicesMade().stream().map(Choice::demand).collect(Collectors.toSet()));
    }

    @Override
    public Table<Supply<D>, D, Choice<Supply<D>, D>> matrix() {
        return ImmutableTable.copyOf(matrix);
    }

    @Override
    public List<Board<Supply<D>, D>> history() {
        return null;
    }

    @Override
    public List<Choice<Supply<D>, D>> choicesMade() {
        return ImmutableList.copyOf(this.choicesMade);
    }

    @Override
    public int score() {
        return choicesMade().stream().mapToInt(Choice::score).sum();
    }

    @Override
    public int boardScore() {
        return availableChoices().stream().mapToInt(Choice::score).sum();
    }
}
