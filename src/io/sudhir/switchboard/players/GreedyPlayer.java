package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.Player;

import java.util.Collection;


public class GreedyPlayer implements Player {
    private final Goal goal;

    public GreedyPlayer(Goal goal) {
        this.goal = goal;
    }

    @Override
    public Choice bestChoiceFor(Board board) {
        return board.availableChoices().parallelStream().max(goal.choiceComparator()).get();
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board) {
        return ImmutableSet.of(bestChoiceFor(board));
    }
}
