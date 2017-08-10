package io.sudhir.switchboard.games;

import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Game;
import io.sudhir.switchboard.Player;

public class SinglePlayerSequentialGame implements Game {
    private final Board board;
    private final Player player;

    public SinglePlayerSequentialGame(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    @Override
    public Board run(Board startingBoard) {
        Board runningBoard = startingBoard;
        while (runningBoard.canProceed() && !runningBoard.isComplete()) {
            runningBoard = runningBoard.choose(player.bestChoiceFor(runningBoard));
        }
        return runningBoard;
    }
}
