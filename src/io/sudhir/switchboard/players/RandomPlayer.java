package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Player;

import java.util.Collection;

public class RandomPlayer implements Player {
    @Override
    public Choice bestChoiceFor(Board board) {
        return board.availableChoices().iterator().next();
    }

    @Override
    public Collection<Choice> goodChoicesFor(Board board) {
        return ImmutableSet.of(bestChoiceFor(board));
    }
}
