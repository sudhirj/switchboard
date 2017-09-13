package io.sudhir.switchboard.games;

import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;

public interface Game {
  // Expected to return the best possible final board, based on the Goal provided
  Board run(Board startingBoard, Goal goal);
}
