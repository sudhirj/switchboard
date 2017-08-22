package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.*;
import io.sudhir.switchboard.boards.ImmutableBoard;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void bestChoiceFor() throws Exception {
        Supply constantSupply = ConstantSupply.create("a");
        Supply optimizingSupply = ReducingRandomSupply.create("a");
        List<Demand> demands = Stream.of("a", "b", "c", "d", "e", "f", "g").map(ConstantDemand::create).collect(toList());
        Board board = ImmutableBoard.create(ImmutableSet.of(constantSupply, optimizingSupply), demands);
        Choice optimisticChoice = new UnreasonablyOptimisticPlayer().bestChoiceFor(board, Goals.MINIMIZE);
        assert optimisticChoice != null;
        assertEquals(optimizingSupply, optimisticChoice.supply());

        Choice greedyChoice = new GreedyPlayer().bestChoiceFor(board, Goals.MINIMIZE);
        assert greedyChoice != null;
        assertEquals(constantSupply, greedyChoice.supply());
    }

    @Test
    public void goodChoiceList() throws Exception {
        Board board = TestBoards.CONSTANT.board();
        Collection<Choice> greedyChoices = new GreedyPlayer().goodChoicesFor(board, Goals.MINIMIZE);
        assertEquals(3, greedyChoices.size());
        assertEquals(42, ImmutableList.copyOf(greedyChoices).get(0).score());
        assertEquals(42, ImmutableList.copyOf(greedyChoices).get(1).score());
        assertEquals(42, ImmutableList.copyOf(greedyChoices).get(2).score());
    }

}