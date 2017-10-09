package io.sudhir.switchboard.boards;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@AutoValue
abstract class ImmutableBoard implements Board {

  public static ImmutableBoard create(Collection<Supply> supplies, Collection<Demand> demands) {
    return new AutoValue_ImmutableBoard(
        ImmutableSet.copyOf(supplies),
        ImmutableSet.copyOf(demands),
        Optional.empty(),
        Optional.empty());
  }

  abstract Set<Supply> supplies();

  abstract Set<Demand> demands();

  @Override
  public abstract Optional<Choice> choice();

  @Override
  public abstract Optional<ImmutableBoard> board();

  @Override
  public Stream<Choice> choicesMade() {
    return history().map(Board::choice).filter(Optional::isPresent).map(Optional::get);
  }

  @Override
  public Stream<Board> history() {
    return Stream.iterate(Optional.of(this), Optional::isPresent, board -> board.get().board())
        .map(Optional::get);
  }

  @Override
  public Board choose(Choice choice) {
    return new AutoValue_ImmutableBoard(
        supplies(), demands(), Optional.of(choice), Optional.of(this));
  }

  @Override
  public boolean isComplete() {
    return !pendingDemands().findAny().isPresent();
  }

  @Override
  public boolean canProceed() {
    return availableChoices().findAny().isPresent();
  }

  @Override
  public Stream<Choice> availableChoices() {
    return pendingDemands().flatMap(this::availableChoices);
  }

  @Override
  public Stream<Choice> availableChoices(Demand demand) {
    return supplies()
        .parallelStream()
        .map(supply -> supply.estimateFor(demand, supplyCommitmentStream(supply)))
        .filter(Optional::isPresent)
        .map(Optional::get);
  }

  @Override
  public Stream<Demand> pendingDemands() {
    ImmutableSet<Demand> metDemands = metDemands().collect(toImmutableSet());
    return demands().parallelStream().filter(demand -> !metDemands.contains(demand));
  }

  private Stream<Demand> metDemands() {
    return choicesMade().parallel().map(Choice::demand);
  }

  private Stream<Choice> supplyCommitmentStream(Supply supply) {
    return choicesMade().parallel().filter(c -> c.supply().equals(supply));
  }

  @Override
  public double score() {
    return choicesMade().parallel().mapToDouble(Choice::score).sum();
  }

  @Override
  public double boardScore() {
    return availableChoices().mapToDouble(Choice::score).sum();
  }

  private <T> ImmutableList<T> append(List<T> items, T item) {
    ImmutableList.Builder<T> builder = ImmutableList.builder();
    builder.addAll(items);
    builder.add(item);
    return builder.build();
  }
}
