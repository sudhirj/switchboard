package io.sudhir.switchboard.explorers;

import com.google.common.collect.ImmutableSortedSet;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class GreedyExplorer implements Explorer {

  private final ConcurrentSkipListSet<Board> discoveries;

  public GreedyExplorer(Goal goal) {
    Comparator<Board> comparator =
        Comparator.comparingLong(Board::workDone).thenComparing(goal.boardComparator());
    this.discoveries = new ConcurrentSkipListSet<>(comparator);
  }

  @Override
  public SortedSet<Board> discoveries() {
    return ImmutableSortedSet.copyOfSorted(discoveries);
  }

  @Override
  public boolean test(Board board) {
    if (isBetterThanExistingDiscoveries(board)) {
      discoveries.add(board);
      return true;
    }
    return false;
  }

  private boolean isBetterThanExistingDiscoveries(Board board) {
    Board betterBoard = discoveries.ceiling(board);
    return betterBoard == null || betterBoard.workDone() > board.workDone();
  }
}
