package io.sudhir.switchboard.players;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;

import javax.annotation.Nullable;
import java.util.Collection;

public interface Player {
    @Nullable
    Choice bestChoiceFor(Board board, Goal goal);

    Collection<Choice> goodChoicesFor(Board board, Goal goal);
}
