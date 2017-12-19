package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public interface Explorer extends Predicate<Board> {

  SortedSet<Board> discoveries();

  default Board explore(Board board) {
    ForkJoinPool pool = new ForkJoinPool();
    pool.submit(new RecursiveBoardExplorationAction(board, this));
    pool.awaitQuiescence(30, TimeUnit.SECONDS);
    return discoveries().isEmpty() ? board : discoveries().last();
  }

  class RecursiveBoardExplorationAction extends RecursiveAction {
    private final Board board;
    private final Explorer explorer;

    RecursiveBoardExplorationAction(Board board, Explorer explorer) {
      this.board = board;
      this.explorer = explorer;
    }

    @Override
    protected void compute() {
      if (explorer.test(board)) {
        board
            .availableChoices()
            .map(board::choose)
            .forEach(b -> invokeAll(new RecursiveBoardExplorationAction(b, explorer)));
      }
    }
  }
}
