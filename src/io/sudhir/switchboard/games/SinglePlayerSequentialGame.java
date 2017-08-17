package io.sudhir.switchboard.games;

import io.sudhir.switchboard.*;

public class SinglePlayerSequentialGame implements Game {
    private final Player player;

    public SinglePlayerSequentialGame(Player player) {
        this.player = player;
    }

    @Override
    public Board run(Board startingBoard, Goal goal) {
        long start = System.currentTimeMillis();
        Board runningBoard = startingBoard;
        while (runningBoard.canProceed() && !runningBoard.isComplete()) {
            long choiceStart = System.currentTimeMillis();
            Choice choice = player.bestChoiceFor(runningBoard, goal);
            long choiceDone = System.currentTimeMillis();
            System.out.println("Choice determination took " + (choiceDone - choiceStart) +" for player " +player );
            runningBoard = runningBoard.choose(choice);
            System.out.println("Board regenration took " + (System.currentTimeMillis() - choiceDone));
        }
        System.out.println("One run took " + (System.currentTimeMillis() - start));
        return runningBoard;
    }
}
