package io.sudhir.switchboard;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.boards.Board;
import io.sudhir.switchboard.games.Game;
import io.sudhir.switchboard.games.SinglePlayerSequentialGame;
import io.sudhir.switchboard.players.GreedyPlayer;
import io.sudhir.switchboard.players.Player;
import io.sudhir.switchboard.players.RandomPlayer;
import io.sudhir.switchboard.players.UnreasonablyOptimisticPlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private Collection<Player> players;

    @Before
    public void setUp() throws Exception {
        players = ImmutableSet.of(
                new RandomPlayer(),
                new GreedyPlayer(),
                new UnreasonablyOptimisticPlayer()
        );
    }

    @Test
    public void simpleRandomGame() {
        Board board = TestBoards.CONSTANT.board();
        for (Player player : players) {
            Game game = new SinglePlayerSequentialGame(player);
            Board finishedBoard = game.run(board, Goals.MINIMIZE);
            assertTrue(finishedBoard.isComplete());
            assertEquals(5, finishedBoard.choicesMade().size());
            assertEquals(42 * 5, finishedBoard.score());
        }
    }

    @Test
    public void benchmarkRunningOnRandomBoard() {
        Board board = TestBoards.RANDOM.board();
        for (Player player : players) {
            Game game = new SinglePlayerSequentialGame(player);
            game.run(board, Goals.MINIMIZE);
        }
    }
}

