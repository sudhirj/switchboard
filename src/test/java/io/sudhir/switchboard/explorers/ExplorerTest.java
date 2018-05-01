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
              long explorationCount = testBoard.exploreWhile(explorer).count();
              long totalCount = testBoard.explore().count();
              System.out.println(
                  "Portion of universe explored: "
                      + explorationCount
                      + " / "
                      + totalCount
                      + " ("
                      + ((double) explorationCount / (double) totalCount * 100.0)
                      + "%)");
              assertTrue(explorer.discoveries().size() > 0);
              Board bestBoard = explorer.discoveries().last();
              assertEquals(210, bestBoard.score(), 0.01);
              assertEquals(testBoard.pendingDemands().count(), bestBoard.workDone());
              assertEquals(210, explorer.explore(testBoard).score(), 0.01);
            });
  }

  @Test
  public void benchmarkForkJoinExploration() {
    List.of(new ComprehensiveExplorer(Goal.MINIMIZE()), new GreedyExplorer(Goal.MINIMIZE()))
        .forEach(
            explorer -> {
              System.out.println("FORKJOIN Running : " + explorer.getClass().toString());
              Board testBoard = TestBoards.RANDOM.board();
              Board bestBoard = explorer.explore(testBoard);
              System.out.println(
                  "FORKJOIN Best Board: " + bestBoard.workDone() + " / " + bestBoard.score());
              System.out.println("FORKJOIN Discoveries : " + explorer.discoveries().size());
              assertEquals(199, bestBoard.workDone());
              assertEquals(8366, bestBoard.score(), 0.1);
            });
  }

  @Test
  public void benchmarkStreamExploration() {
    List.of(new ComprehensiveExplorer(Goal.MINIMIZE()), new GreedyExplorer(Goal.MINIMIZE()))
        .forEach(
            explorer -> {
              System.out.println("STREAM Running : " + explorer.getClass().toString());
              Board testBoard = TestBoards.RANDOM.board();
              testBoard.exploreWhile(explorer).count();
              Board bestBoard = explorer.discoveries().last();
              System.out.println(
                  "STREAM Best Board: " + bestBoard.workDone() + " / " + bestBoard.score());
              System.out.println("STREAM Discoveries : " + explorer.discoveries().size());
              assertEquals(199, bestBoard.workDone());
              assertEquals(8366, bestBoard.score(), 0.1);
            });
  }
}
