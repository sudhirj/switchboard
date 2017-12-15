package io.sudhir.switchboard.explorers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.TestBoards;
import io.sudhir.switchboard.boards.Board;
import java.util.List;
import org.junit.Test;

public class ExplorerTest {

  @Test
  public void discoveries() {
    List.of(new ComprehensiveExplorer(Goal.MINIMIZE()), new GreedyExplorer(Goal.MINIMIZE()))
        .forEach(
            explorer -> {
              System.out.println("Running : " + explorer.getClass().toString());
              Board testBoard = TestBoards.CONSTANT.board();
              double explorationCount = testBoard.exploreWhile(explorer).count();
              double totalCount = testBoard.explore().count();
              System.out.println(((explorationCount / totalCount) * 100) + "%");
              assertTrue(explorer.discoveries().size() > 0);
              Board bestBoard = explorer.discoveries().last();
              assertEquals(210, bestBoard.score(), 0.01);
              assertEquals(testBoard.pendingDemands().count(), bestBoard.workDone());
            });
  }

  @Test
  public void benchmark() {
    List.of(new ComprehensiveExplorer(Goal.MINIMIZE()), new GreedyExplorer(Goal.MINIMIZE()))
        .forEach(
            explorer -> {
              System.out.println("Running : " + explorer.getClass().toString());
              Board testBoard = TestBoards.RANDOM.board();
              System.out.println("Explored : " + testBoard.exploreWhile(explorer).count());
              System.out.println("Discoveries : " + explorer.discoveries().size());
            });
  }
}
