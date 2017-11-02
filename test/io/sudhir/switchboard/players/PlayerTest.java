package io.sudhir.switchboard.players;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.ConstantDemand;
import io.sudhir.switchboard.ConstantSupply;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Goal;
import io.sudhir.switchboard.ReducingRandomSupply;
import io.sudhir.switchboard.Supply;
import io.sudhir.switchboard.TestBoards;
import io.sudhir.switchboard.boards.Board;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;

public class PlayerTest {

  @Test
  public void bestChoiceFor() throws Exception {
    Supply constantSupply = ConstantSupply.create("a");
    Supply optimizingSupply = ReducingRandomSupply.create("a");
    List<Demand> demands =
        Stream.of("a", "b", "c", "d", "e", "f", "g").map(ConstantDemand::create).collect(toList());
    Board board = Board.create(ImmutableSet.of(constantSupply, optimizingSupply), demands);
    Choice optimisticChoice =
        new UnreasonablyOptimisticPlayer().bestChoiceFor(board, Goal.MINIMIZE()).get();
    assertNotNull(optimisticChoice);
    assertEquals(optimizingSupply, optimisticChoice.supply());

    Choice greedyChoice = new GreedyPlayer().bestChoiceFor(board, Goal.MINIMIZE()).get();
    assertNotNull(greedyChoice);
    assertEquals(constantSupply, greedyChoice.supply());

    Choice maxGreedyChoice = new GreedyPlayer().bestChoiceFor(board, Goal.MAXIMIZE()).get();
    assertNotNull(maxGreedyChoice);
    assertEquals(optimizingSupply, maxGreedyChoice.supply());
  }

  @Test
  public void goodChoiceList() throws Exception {
    Board board = TestBoards.CONSTANT.board();
    Collection<Choice> greedyChoices =
        board
            .availableChoices()
            .sorted(Goal.MINIMIZE().choiceComparator().reversed())
            .limit(3)
            .collect(toImmutableSet());
    assertEquals(3, greedyChoices.size());
    assertEquals(42, ImmutableList.copyOf(greedyChoices).get(0).score(), 0.01);
    assertEquals(42, ImmutableList.copyOf(greedyChoices).get(1).score(), 0.01);
    assertEquals(42, ImmutableList.copyOf(greedyChoices).get(2).score(), 0.01);
  }
}
