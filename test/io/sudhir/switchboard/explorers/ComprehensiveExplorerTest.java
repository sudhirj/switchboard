package io.sudhir.switchboard.explorers;

import static org.junit.Assert.assertTrue;

import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.TestBoards;
import io.sudhir.switchboard.boards.Board;
import java.util.Optional;
import org.junit.Test;

public class ComprehensiveExplorerTest {

  @Test
  public void discoveries() {
    Optional<Board> cheapest = TestBoards.CONSTANT
        .board().exploreWhile(new ComprehensiveExplorer(Goal.MINIMIZE()))
        .max(Goal.MINIMIZE().boardComparator());
    assertTrue(cheapest.isPresent());
    System.out.println(cheapest.get().score());
  }
}