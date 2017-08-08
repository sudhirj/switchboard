import com.google.common.collect.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImmutableBoard<S extends Supply<D>, D extends Demand> implements Board<Supply<D>, D> {

    private final ImmutableSet<S> supplies;
    private final ImmutableSet<D> demands;
    private final ImmutableTable<Supply<D>, D, Choice<Supply<D>, D>> matrix;
    private final ImmutableList<Choice<Supply<D>, D>> choicesMade;
    private final ImmutableList<Board<Supply<D>, D>> history;

    ImmutableBoard(Collection<S> supplies, Collection<D> demands) {
        this(supplies, demands, ImmutableList.of(), ImmutableList.of());
    }

    private ImmutableBoard(Collection<S> supplies, Collection<D> demands, List<Choice<Supply<D>, D>> choicesMade, List<Board<Supply<D>, D>> history) {
        this.supplies = ImmutableSet.copyOf(supplies);
        this.demands = ImmutableSet.copyOf(demands);
        this.choicesMade = ImmutableList.copyOf(choicesMade);
        this.history = ImmutableList.copyOf(history);
        this.matrix = buildMatrix();
    }

    private ImmutableTable<Supply<D>, D, Choice<Supply<D>, D>> buildMatrix() {
        ImmutableTable.Builder<Supply<D>, D, Choice<Supply<D>, D>> builder = ImmutableTable.builder();
        for (S supply : supplies) {
            for (D demand : demands) {
                Choice<Supply<D>, D> estimate = supply.estimateFor(demand, choicesMade().stream().filter(c -> c.supply().equals(supply)).collect(Collectors.toList()));
                if (estimate != null) {
                    builder.put(supply, demand, estimate);
                }
            }
        }
        return builder.build();
    }

    @Override
    public Board<Supply<D>, D> choose(Choice<Supply<D>, D> choice) {
        return new ImmutableBoard<>(supplies, demands, append(choicesMade(), choice), append(history(), this));
    }

    @Override
    public boolean isComplete() {
        return pendingDemands().isEmpty();
    }

    @Override
    public boolean canProceed() {
        return !availableChoices().isEmpty();
    }

    @Override
    public Collection<Choice<Supply<D>, D>> availableChoices() {
        return Sets.difference(ImmutableSet.copyOf(matrix.values()), ImmutableSet.copyOf(choicesMade()));
    }

    @Override
    public Collection<D> pendingDemands() {
        return Sets.difference(demands, choicesMade().parallelStream().map(Choice::demand).collect(Collectors.toSet()));
    }

    @Override
    public Table<Supply<D>, D, Choice<Supply<D>, D>> matrix() {
        return ImmutableTable.copyOf(matrix);
    }

    @Override
    public List<Board<Supply<D>, D>> history() {
        return ImmutableList.copyOf(history);
    }

    @Override
    public List<Choice<Supply<D>, D>> choicesMade() {
        return ImmutableList.copyOf(choicesMade);
    }

    @Override
    public int score() {
        return choicesMade().parallelStream().mapToInt(Choice::score).sum();
    }

    @Override
    public int boardScore() {
        return availableChoices().parallelStream().mapToInt(Choice::score).sum();
    }


    private <T> ImmutableList<T> append(List<T> items, T item) {
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        builder.addAll(items);
        builder.add(item);
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableBoard<?, ?> that = (ImmutableBoard<?, ?>) o;

        return supplies.equals(that.supplies) && demands.equals(that.demands) && matrix.equals(that.matrix) && choicesMade.equals(that.choicesMade) && history.equals(that.history);
    }

    @Override
    public int hashCode() {
        int result = supplies.hashCode();
        result = 31 * result + demands.hashCode();
        result = 31 * result + matrix.hashCode();
        result = 31 * result + choicesMade.hashCode();
        result = 31 * result + history.hashCode();
        return result;
    }
}
