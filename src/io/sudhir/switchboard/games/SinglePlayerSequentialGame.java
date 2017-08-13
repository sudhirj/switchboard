package io.sudhir.switchboard.games;

import io.sudhir.switchboard.*;

public class SinglePlayerSequentialGame implements Game {
    private final Player player;

    public SinglePlayerSequentialGame(Player player) {
        this.player = player;
    }

    @Override
    public Board run(Board startingBoard, Goal goal) {
        Board runningBoard = startingBoard;
        while (runningBoard.canProceed() && !runningBoard.isComplete()) {
            long start = System.currentTimeMillis();
            Choice choice = player.bestChoiceFor(runningBoard, goal);
            long choiceCheckpoint = System.currentTimeMillis();
            System.out.println("Choice decided in " + (choiceCheckpoint - start));
            runningBoard = runningBoard.choose(choice);
            long boardCheckpoint = System.currentTimeMillis();
            System.out.println("New board generated in " + (boardCheckpoint - choiceCheckpoint));
        }
        return runningBoard;
    }
}
