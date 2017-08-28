package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class UnreasonablyOptimisticPlayer implements Player {

    private final double reductionFactor;

    public UnreasonablyOptimisticPlayer() {
        this(10);
    }

    public UnreasonablyOptimisticPlayer(double reductionFactor) {
        this.reductionFactor = reductionFactor;
    }

    @Nullable
    @Override
    public Choice bestChoiceFor(Board board, Goal goal) {
        return goodChoicesFor(board, goal).stream().findFirst().orElse(null);
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board, Goal goal) {
        long limit = Double.valueOf(Math.ceil((double) board.availableChoices().size() / reductionFactor)).longValue();
        return board.availableChoices().parallelStream().sorted(goal.choiceComparator()).limit(limit).reduce(
                new ConcurrentSkipListMap<Integer, Choice>(goal.comparator()),
                (diffMap, choice) -> {
                    diffMap.put(board.boardScore() - board.choose(choice).boardScore(), choice);
                    return diffMap;
                },
                (m1, m2) -> m1).values().stream().limit(3).collect(toImmutableList());
    }
}
