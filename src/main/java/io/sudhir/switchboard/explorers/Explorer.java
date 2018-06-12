package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Predicate;

public abstract class Explorer implements Predicate<Board> {

  private LongAdder counter = new LongAdder();

  protected ForkJoinPool forkJoinPool() {
    return ForkJoinPool.commonPool();
  }

  public abstract SortedSet<Board> discoveries();

  long count() {
    return counter.longValue();
  }

  protected Board explore(Board board) {
    if (test(board)) {
      forkJoinPool().invoke(new RecursiveBoardExplorationAction(board));
    }
    // Assume the sorted set is always sorted in ascending order of preference.
    return discoveries().isEmpty() ? board : discoveries().last();
  }

  class RecursiveBoardExplorationAction extends RecursiveAction {

    private final Board board;

    private RecursiveBoardExplorationAction(Board board) {
      this.board = board;
    }

    @Override
    protected void compute() {
      board
          .availableChoices()
          .map(board::choose)
          .forEach(
              newBoard -> {
                if (Explorer.this.test(newBoard)) {
                  Explorer.this.counter.increment();
                  forkJoinPool().invoke(new RecursiveBoardExplorationAction(newBoard));
                }
              });
    }
  }
}
