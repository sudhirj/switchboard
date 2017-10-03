package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Optional;

public interface Player {
  Optional<Choice> bestChoiceFor(Board board, Goal goal);
}
