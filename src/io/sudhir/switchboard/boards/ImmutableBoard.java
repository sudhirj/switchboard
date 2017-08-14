package io.sudhir.switchboard.boards;

import com.google.common.collect.*;
import io.sudhir.switchboard.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ImmutableBoard implements Board {

    private final ImmutableSet<Supply> supplies;
    private final ImmutableSet<Demand> demands;
    private final ImmutableTable<Supply, Demand, Choice> matrix;
    private final ImmutableList<Choice> choicesMade;
    private final ImmutableList<Board> history;
    private final ImmutableSet<Supply> alwaysMutableSupplies;

    public ImmutableBoard(Collection<Supply> supplies, Collection<Demand> demands) {
        this(supplies, demands, ImmutableList.of(), ImmutableList.of(), supplies.parallelStream().filter(s -> s.recheckStrategy() == RecheckStrategy.ALWAYS).collect(Collectors.toSet()));
    }

    private ImmutableBoard(Collection<Supply> supplies, Collection<Demand> demands, List<Choice> choicesMade, List<Board> history, Collection<Supply> alwaysMutableSupplies) {
        this.supplies = ImmutableSet.copyOf(supplies);
        this.alwaysMutableSupplies = ImmutableSet.copyOf(alwaysMutableSupplies);
        this.demands = ImmutableSet.copyOf(demands);
        this.choicesMade = ImmutableList.copyOf(choicesMade);
        this.history = ImmutableList.copyOf(history);
        this.matrix = buildMatrix();
    }

    private ImmutableTable<Supply, Demand, Choice> buildMatrix() {

        Set<Supply> suppliesToCompute = new HashSet<>(history().size() > 0 ? alwaysMutableSupplies : supplies);

        if (choicesMade().size() > 0) {
            Supply lastCommittedSupply = Iterables.getLast(choicesMade()).supply();
            if (lastCommittedSupply.recheckStrategy() == RecheckStrategy.ON_COMMITMENT) {
                suppliesToCompute.add(lastCommittedSupply);
            }
        }

        HashBasedTable<Supply, Demand, Choice> temporaryTable = startingTable();
        Set<Demand> metDemands = choicesMade.parallelStream().map(Choice::demand).collect(Collectors.toSet());
        Set<Demand> unmetDemands = Sets.difference(demands, metDemands);
        for (Supply supply : suppliesToCompute) {
            List<Choice> relevantChoices = choicesMade().parallelStream().filter(c -> c.supply().equals(supply)).collect(Collectors.toList());
            for (Demand demand : unmetDemands) {
                Choice estimate = supply.estimateFor(demand, relevantChoices);
                if (estimate != null) {
                    temporaryTable.put(supply, demand, estimate);
                }
            }
        }
        return ImmutableTable.copyOf(temporaryTable);
    }

    private HashBasedTable<Supply, Demand, Choice> startingTable() {
        if (history().size() > 0) {
            return HashBasedTable.create(Iterables.getLast(history()).matrix());
        }
        return HashBasedTable.create();
    }

    @Override
    public Board choose(Choice choice) {
        return new ImmutableBoard(supplies, demands, append(choicesMade(), choice), append(history(), this), alwaysMutableSupplies);
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
    public Collection<Choice> availableChoices() {
        return Sets.difference(ImmutableSet.copyOf(matrix.values()), ImmutableSet.copyOf(choicesMade()));
    }

    @Override
    public Collection<Demand> pendingDemands() {
        return Sets.difference(demands, choicesMade().parallelStream().map(Choice::demand).collect(Collectors.toSet()));
    }

    @Override
    public Table<Supply, Demand, Choice> matrix() {
        return ImmutableTable.copyOf(matrix);
    }

    @Override
    public List<Board> history() {
        return ImmutableList.copyOf(history);
    }

    @Override
    public List<Choice> choicesMade() {
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

        ImmutableBoard that = (ImmutableBoard) o;

        if (!supplies.equals(that.supplies)) return false;
        if (!demands.equals(that.demands)) return false;
        if (!matrix.equals(that.matrix)) return false;
        if (!choicesMade.equals(that.choicesMade)) return false;
        return history.equals(that.history);
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
