package io.sudhir.switchboard.boards;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

@AutoValue
abstract class ImmutableBoard implements Board {

  public static ImmutableBoard create(Collection<Supply> supplies, Collection<Demand> demands) {
    return new AutoValue_ImmutableBoard(
        ImmutableSet.copyOf(supplies),
        ImmutableSet.copyOf(demands),
        ImmutableList.of(),
        ImmutableList.of());
  }

  abstract Set<Supply> supplies();

  abstract Set<Demand> demands();

  @Override
  public abstract List<Choice> choicesMade();

  @Override
  public abstract List<Board> history();

  @Override
  public Board choose(Choice choice) {
    return new AutoValue_ImmutableBoard(
        supplies(), demands(), append(choicesMade(), choice), append(history(), this));
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
  @Memoized
  public Collection<Choice> availableChoices() {
    return supplies()
        .parallelStream()
        .flatMap(
            supply -> {
              List<Choice> committedChoices =
                  choicesMade()
                      .parallelStream()
                      .filter(c -> c.supply().equals(supply))
                      .collect(toImmutableList());
              return pendingDemands()
                  .parallelStream()
                  .map(demand -> supply.estimateFor(demand, committedChoices));
            })
        .filter(Objects::nonNull)
        .collect(toImmutableSet());
  }

  @Override
  public Collection<Demand> pendingDemands() {
    return Sets.difference(
        demands(), choicesMade().parallelStream().map(Choice::demand).collect(toImmutableSet()));
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
}
