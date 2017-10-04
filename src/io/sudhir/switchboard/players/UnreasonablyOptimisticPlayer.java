package io.sudhir.switchboard.players;

import static com.google.common.collect.ImmutableList.toImmutableList;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

public class UnreasonablyOptimisticPlayer implements Player {

  UnreasonablyOptimisticPlayer() {}

  @Override
  public Optional<Choice> bestChoiceFor(Board board, Goal goal) {
    return goodChoicesFor(board, goal).stream().findFirst();
  }

  private Collection<Choice> goodChoicesFor(Board board, Goal goal) {

    return board
        .availableChoices()
        .sorted(goal.choiceComparator())
        .reduce(
            new ConcurrentSkipListMap<Double, Choice>(goal.comparator()),
            (diffMap, choice) -> {
              diffMap.put(board.boardScore() - board.choose(choice).boardScore(), choice);
              return diffMap;
            },
            (m1, m2) -> m1)
        .values()
        .stream()
        .limit(3)
        .collect(toImmutableList());
  }
}
