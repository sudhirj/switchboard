package io.sudhir.switchboard.games;

import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Game;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.Player;

public class SinglePlayerSequentialGame implements Game {
    private final Player player;

    public SinglePlayerSequentialGame(Player player) {
        this.player = player;
    }

    @Override
    public Board run(Board startingBoard, Goal goal) {
        Board runningBoard = startingBoard;
        while (runningBoard.canProceed() && !runningBoard.isComplete()) {
            runningBoard = runningBoard.choose(player.bestChoiceFor(runningBoard, goal));
        }
        return runningBoard;
    }
}
