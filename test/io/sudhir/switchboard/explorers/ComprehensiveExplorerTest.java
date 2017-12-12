package io.sudhir.switchboard.explorers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.TestBoards;
import io.sudhir.switchboard.boards.Board;
import org.junit.Test;

public class ComprehensiveExplorerTest {

  @Test
  public void discoveries() {
    Board testBoard = TestBoards.CONSTANT.board();
    ComprehensiveExplorer explorer = new ComprehensiveExplorer(Goal.MINIMIZE());
    double explorationCount = testBoard.exploreWhile(explorer).count();
    double totalCount = testBoard.explore().count();
    System.out.println(((explorationCount / totalCount) * 100) + "%");
    assertTrue(explorer.discoveries().size() > 0);
    Board bestBoard = explorer.discoveries().first();
    assertEquals(210, bestBoard.score(), 0.01);
    assertEquals(testBoard.pendingDemands().count(), bestBoard.length());
  }

  @Test
  public void benchmark() {
    Board testBoard = TestBoards.RANDOM.board();
    ComprehensiveExplorer explorer = new ComprehensiveExplorer(Goal.MINIMIZE());
    double explorationCount = testBoard.exploreWhile(explorer).count();
    System.out.println("Explored : " + explorationCount);
  }
}