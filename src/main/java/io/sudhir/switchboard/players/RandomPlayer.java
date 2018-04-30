package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Optional;

public class RandomPlayer implements Player {

  @Override
  public Optional<Choice> bestChoiceFor(Board board, Goal goal) {
    return board.availableChoices().limit(1).findAny();
  }
}
