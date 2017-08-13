package io.sudhir.switchboard;

import javax.annotation.Nullable;
import java.util.Collection;

public interface Player {
    @Nullable
    Choice bestChoiceFor(Board board, Goal goal);

    Collection<Choice> goodChoicesFor(Board board, Goal goal);
}
