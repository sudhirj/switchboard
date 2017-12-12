package io.sudhir.switchboard.explorers;

import com.google.common.collect.ImmutableSortedSet;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.boards.Board;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;


public class ComprehensiveExplorer implements Explorer {

  private final ConcurrentSkipListSet<Board> discoveries;

  public ComprehensiveExplorer(Goal goal) {
    Comparator<Board> comparator = Comparator.comparingLong(Board::length)
        .thenComparing(goal.boardComparator());
    this.discoveries = new ConcurrentSkipListSet<>(
        comparator);
  }

  @Override
  public SortedSet<Board> discoveries() {
    return ImmutableSortedSet.copyOfSorted(discoveries);
  }

  @Override
  public boolean test(Board board) {
    if (board.canProceed()) {
      System.out.println("Checking board " + board.length() + " / " + board.score());
      System.out.println("Currently have " + discoveries.size());
      return noDiscoveriesAvailableForComparison()
          || boardIsMorePromisingThanBestDiscovery(board);
    }
    if (boardIsMorePromisingThanBestDiscovery(board)) {
      System.out.println("Discovered " + board.score());
      discoveries.add(board);
    }
    return false;
  }

  private boolean boardIsMorePromisingThanBestDiscovery(Board board) {
    return discoveries.higher(board) == null;
  }

  private boolean noDiscoveriesAvailableForComparison() {
    return discoveries.size() <= 0;
  }
}
