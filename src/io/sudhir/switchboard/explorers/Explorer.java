package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Predicate;

public interface Explorer extends Predicate<Board> {

  static ForkJoinPool forkJoinPool() {
    return ForkJoinPool.commonPool();
  }

  SortedSet<Board> discoveries();

  default Board explore(Board board) {
    if (test(board)) {
      forkJoinPool().invoke(new RecursiveBoardExplorationAction(board, this));
    }
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
      board
          .availableChoices()
          .map(board::choose)
          .forEach(
              b -> {
                if (explorer.test(b)) {
                  forkJoinPool().invoke(new RecursiveBoardExplorationAction(b, explorer));
                }
              });
    }
  }
}
