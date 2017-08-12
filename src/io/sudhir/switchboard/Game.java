package io.sudhir.switchboard;

public interface Game {
    // Expected to return the best possible final board, based on the Goal provided
    Board run(Board startingBoard, Goal goal);
}
