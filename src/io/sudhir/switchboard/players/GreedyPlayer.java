package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Optional;

public class GreedyPlayer implements Player {

  @Override
  public Optional<Choice> bestChoiceFor(Board board, Goal goal) {
    return board
        .viableDemands()
        .limit(1)
        .flatMap(board::availableChoices)
        .max(goal.choiceComparator());
  }
}
