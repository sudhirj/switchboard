package io.sudhir.switchboard.players;

import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.*;
import io.sudhir.switchboard.boards.ImmutableBoard;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void bestChoiceFor() throws Exception {
        Supply constantSupply = new Supply() {
            @Override
            public Choice estimateFor(Demand demand, List<Choice> commitments) {
                return Choice.create(this, demand, 42);
            }

            @Override
            public RecheckStrategy recheckStrategy() {
                return RecheckStrategy.ON_COMMITMENT;
            }
        };
        Supply optimizingSupply = new Supply() {
            @Override
            public Choice estimateFor(Demand demand, List<Choice> commitments) {
                return Choice.create(this, demand, commitments.size() > 0 ? 10 : 100);
            }

            @Override
            public RecheckStrategy recheckStrategy() {
                return RecheckStrategy.ON_COMMITMENT;
            }
        };
        List<Demand> demands = Stream.of("a", "b", "c", "d", "e", "f", "g").map(ConstantDemand::create).collect(toList());
        Board board = new ImmutableBoard(ImmutableSet.of(constantSupply, optimizingSupply), demands);
        Choice optimisticChoice = new OptimisticPlayer().bestChoiceFor(board, Goals.MINIMIZE);
        assert optimisticChoice != null;
        assertEquals(optimizingSupply, optimisticChoice.supply());

        Choice greedyChoice = new GreedyPlayer().bestChoiceFor(board, Goals.MINIMIZE);
        assert greedyChoice != null;
        assertEquals(constantSupply, greedyChoice.supply());

    }

}