package io.sudhir.switchboard;

import java.util.Collection;

public interface Player {
    Choice bestChoiceFor(Board board, Goal goal);

    Collection<Choice> goodChoicesFor(Board board, Goal goal);
}
