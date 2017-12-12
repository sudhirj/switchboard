package io.sudhir.switchboard;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.boards.Board;
import io.sudhir.switchboard.games.Game;
import io.sudhir.switchboard.games.SinglePlayerSequentialGame;
import io.sudhir.switchboard.players.GreedyPlayer;
import io.sudhir.switchboard.players.Player;
import io.sudhir.switchboard.players.RandomPlayer;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

  private Collection<Player> players;
  private Board randomBoard;

  @Before
  public void setUp() {
    players = ImmutableSet.of(new RandomPlayer(), new GreedyPlayer());
    randomBoard = TestBoards.RANDOM.board();
  }

  @Test
  public void simpleRandomGame() {
    Board board = TestBoards.CONSTANT.board();
    for (Player player : players) {
      Game game = new SinglePlayerSequentialGame(player);
      Board finishedBoard = game.run(board, Goals.MINIMIZE);
      assertTrue(finishedBoard.isComplete());
      assertEquals(5, finishedBoard.choicesMade().collect(toImmutableList()).size());
      assertEquals(42 * 5, finishedBoard.score(), 0.01);
    }
  }

  @Test
  public void benchmarkPlayerGameModel() {
    for (Player player : players) {
      Game game = new SinglePlayerSequentialGame(player);
//      game.run(randomBoard, Goals.MINIMIZE);
    }
  }
}
