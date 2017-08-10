package io.sudhir.switchboard;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.boards.ImmutableBoard;
import io.sudhir.switchboard.games.SinglePlayerSequentialGame;
import io.sudhir.switchboard.players.GreedyPlayer;
import io.sudhir.switchboard.players.RandomPlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private List<Demand> demands;
    private List<Supply> supplies;
    private ImmutableBoard board;

    @Before
    public void setUp() throws Exception {
        Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
        supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
        board = new ImmutableBoard(supplies, demands);
    }

    @Test
    public void simpleRandomGame() {
        Collection<Player> players = ImmutableSet.of(
                new RandomPlayer(),
                new GreedyPlayer(Goals.MINIMIZE)
        );
        for (Player player : players) {
            Game game = new SinglePlayerSequentialGame(board, player);
            Board finishedBoard = game.run(board);
            assertTrue(finishedBoard.isComplete());
            assertEquals(5, finishedBoard.choicesMade().size());
            assertEquals(42 * 5, finishedBoard.score());
        }
    }
}
