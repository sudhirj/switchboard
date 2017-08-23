package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.Player;

import javax.annotation.Nullable;
import java.util.Collection;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

public class GreedyPlayer implements Player {
    @Override
    @Nullable
    public Choice bestChoiceFor(Board board, Goal goal) {
        return board.availableChoices().parallelStream().max(goal.choiceComparator()).orElse(null);
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board, Goal goal) {
        return board.availableChoices().parallelStream().sorted(goal.choiceComparator().reversed()).limit(3).collect(toImmutableSet());
    }
}
