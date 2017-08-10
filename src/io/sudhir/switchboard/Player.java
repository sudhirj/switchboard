package io.sudhir.switchboard;

import java.util.Collection;

public interface Player {
    Choice bestChoiceFor(Board board);

    Collection<Choice> goodChoicesFor(Board board);
}
