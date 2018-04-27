package io.sudhir.switchboard.explorers;

import io.sudhir.switchboard.boards.Board;
import java.util.SortedSet;

public class ToleranceExplorer extends Explorer {

  @Override
  public SortedSet<Board> discoveries() {
    return null;
  }

  @Override
  public boolean test(Board board) {
    return false;
  }
}
