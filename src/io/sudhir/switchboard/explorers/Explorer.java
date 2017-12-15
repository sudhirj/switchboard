package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;
import java.util.function.Predicate;

public interface Explorer extends Predicate<Board> {
  SortedSet<Board> discoveries();

  default Board explore(Board board) {
    board.exploreWhile(this).count();
    return discoveries().isEmpty() ? board : discoveries().last();
  }
}
