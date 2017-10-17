package io.sudhir.switchboard.boards;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;

@AutoValue
abstract class ImmutableBoard implements Board {

  static ImmutableBoard create(Collection<Supply> supplies, Collection<Demand> demands) {
    return new AutoValue_ImmutableBoard(
        ImmutableSet.copyOf(supplies),
        ImmutableSet.copyOf(demands),
        Optional.empty(),
        Optional.empty());
  }

  abstract Set<Supply> supplies();

  abstract Set<Demand> demands();

  @Override
  @Nullable
  public abstract Optional<Board> board();

  @Override
  @Nullable
  public abstract Optional<Choice> choice();

  @Override
  public Stream<Choice> choicesMade() {
    return history().map(Board::choice).filter(Optional::isPresent).map(Optional::get);
  }

  @Override
  public Stream<Board> history() {
    return Stream.iterate(
        Optional.<Board>of(this), Optional::isPresent, board -> board.get().board())
        .map(Optional::get);
  }

  @Override
  public Board choose(Choice choice) {
    return new AutoValue_ImmutableBoard(
        supplies(), demands(), Optional.of(this), Optional.of(choice));
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
        .map(supply -> supply.estimateFor(demand, choicesMade()))
        .filter(Optional::isPresent)
        .map(Optional::get);
  }

  @Override
  public Stream<Demand> pendingDemands() {
    return Sets.difference(demands(), choicesMade().map(Choice::demand).collect(toImmutableSet()))
        .parallelStream();
  }

  @Override
  public double score() {
    return choicesMade().parallel().mapToDouble(Choice::score).sum();
  }

  @Override
  public double boardScore() {
    return availableChoices().mapToDouble(Choice::score).sum();
  }
}
