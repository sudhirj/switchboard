package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;
import java.util.function.Predicate;

public interface Explorer extends Predicate<Board> {

  SortedSet<Board> discoveries();
}
