package io.sudhir.switchboard;

import java.util.Comparator;

public interface Goal {
    Comparator<Board> boardComparator();

    Comparator<Choice> choiceComparator();
}
