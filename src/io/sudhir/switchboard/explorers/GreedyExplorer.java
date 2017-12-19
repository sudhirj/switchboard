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
    Board existingDiscovery = equallyGoodOrBetterBoard(board);
    return // Board is better if
        // there is no existing discovery
        existingDiscovery == null
            ||
            // or if the existing discovery has done more work
            existingDiscovery.workDone() > board.workDone();
  }

  private Board equallyGoodOrBetterBoard(Board board) {
    return discoveries.ceiling(board);
  }
}
