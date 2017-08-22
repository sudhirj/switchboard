package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;

public class UnreasonablyOptimisticPlayer implements Player {

    private final double restrictionfactor;

    public UnreasonablyOptimisticPlayer() {
        restrictionfactor = 20;
    }

    @Nullable
    @Override
    public Choice bestChoiceFor(Board board, Goal goal) {
        long limit = Double.valueOf(Math.ceil((double) board.availableChoices().size() / restrictionfactor)).longValue();
        return board.availableChoices().parallelStream().sorted(goal.choiceComparator()).limit(limit).reduce(
                new ConcurrentSkipListMap<Integer, Choice>(goal.comparator()),
                (diffMap, choice) -> {
                    diffMap.put(board.boardScore() - board.choose(choice).boardScore(), choice);
                    return diffMap;
                },
                (m1, m2) -> m1).firstEntry().getValue();
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board, Goal goal) {
        return ImmutableSet.of(bestChoiceFor(board, goal));
    }
}
