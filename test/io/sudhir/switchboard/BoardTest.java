package io.sudhir.switchboard;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.sudhir.switchboard.boards.Board;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
  private List<Demand> demands;
  private List<Supply> supplies;
  private Board board;

  @Before
  public void setUp() throws Exception {
    Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
    supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
    demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
    board = Board.create(supplies, demands);
  }

  @Test
  public void availableChoices() throws Exception {
    assertEquals(5, board.availableChoices().collect(toImmutableSet()).size());
    Choice firstChoice =
        ImmutableList.copyOf(board.availableChoices().collect(toImmutableSet())).get(0);
    Board newBoard = board.choose(firstChoice);
    assertEquals(4, newBoard.availableChoices().collect(toImmutableSet()).size());
    assertFalse(newBoard.availableChoices().collect(toImmutableSet()).contains(firstChoice));
  }

  @Test
  public void choose() throws Exception {
    Choice firstChoice = board.availableChoices().iterator().next();
    Board newBoard = board.choose(firstChoice);
    assertEquals(ImmutableList.of(firstChoice), newBoard.choicesMade().collect(toImmutableList()));
  }

  @Test
  public void completenessAndScoring() throws Exception {
    Board currentBoard = board;
    assertFalse(currentBoard.isComplete());
    assertTrue(currentBoard.canProceed());
    assertEquals(42 * 5, currentBoard.boardScore(), 0.01);
    assertEquals(0, currentBoard.score(), 0.01);

    while (currentBoard.availableChoices().findAny().isPresent()) {
      assertTrue(currentBoard.canProceed());
      assertFalse(currentBoard.isComplete());
      currentBoard = currentBoard.choose(currentBoard.availableChoices().iterator().next());
    }

    assertTrue(currentBoard.isComplete());
    assertFalse(currentBoard.canProceed());
    assertEquals(0, currentBoard.boardScore(), 0.01);
    assertEquals(42 * 5, currentBoard.score(), 0.01);
  }

  @Test
  public void unmetDemands() throws Exception {
    assertEquals(
        ImmutableSet.copyOf(board.pendingDemands().collect(toImmutableSet())),
        ImmutableSet.copyOf(demands));
    Choice firstChoice = board.availableChoices().iterator().next();
    assertTrue(board.pendingDemands().collect(toImmutableSet()).contains(firstChoice.demand()));
    Board newBoard = board.choose(firstChoice);
    assertFalse(newBoard.pendingDemands().collect(toImmutableSet()).contains(firstChoice.demand()));
  }

  @Test
  public void history() throws Exception {
    Board firstBoard = board;
    Board secondBoard = firstBoard.choose(firstBoard.availableChoices().iterator().next());
    assertEquals(
        ImmutableList.of(secondBoard, firstBoard),
        secondBoard.history().collect(toImmutableList()));
    Board thirdBoard = secondBoard.choose(secondBoard.availableChoices().iterator().next());
    assertEquals(
        ImmutableList.of(thirdBoard, secondBoard, firstBoard),
        thirdBoard.history().collect(toImmutableList()));
  }
}
