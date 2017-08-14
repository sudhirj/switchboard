package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.Player;

import javax.annotation.Nullable;
import java.util.Collection;

public class OptimisticPlayer implements Player {
    @Nullable
    @Override
    public Choice bestChoiceFor(Board board, Goal goal) {
        return board.availableChoices().parallelStream().reduce((choice1, choice2) ->
                goal.comparator().compare(
                        board.choose(choice1).boardScore(),
                        board.choose(choice2).boardScore()
                ) > 0 ? choice1 : choice2).orElse(null);
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board, Goal goal) {
        return ImmutableSet.of(bestChoiceFor(board, goal));
    }
}
