package io.sudhir.switchboard;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import io.sudhir.switchboard.boards.Board;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

  private List<Demand> demands;
  private List<Supply> supplies;
  private Board board;

  @Before
  public void setUp() {
    Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
    supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
    demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
    board = Board.create(supplies, demands);
  }

  @Test
  public void expansion() {
    Board currentBoard = board;
    while (currentBoard.availableChoices().findAny().isPresent()) {
      currentBoard = currentBoard.choose(currentBoard.availableChoices().iterator().next());
    }
    assertTrue(currentBoard.isComplete());
    Board expandedBoard = currentBoard.expand(
        Set.of(ConstantDemand.create("x"), ConstantDemand.create("y"), ConstantDemand.create("z")));
    assertFalse(expandedBoard.isComplete());
    assertEquals(3, expandedBoard.pendingDemands().collect(toImmutableList()).size());
    assertEquals(currentBoard.score(), expandedBoard.score(), 0.01);
    assertEquals(currentBoard.boardScore(), expandedBoard.boardScore(), 0.01);
    assertEquals(currentBoard.choicesMade().collect(toImmutableSet()),
        expandedBoard.choicesMade().collect(toImmutableSet()));
  }

  @Test
  public void availableChoices() {
    assertEquals(5, board.availableChoices().collect(toImmutableSet()).size());
    Choice firstChoice =
        ImmutableList.copyOf(board.availableChoices().collect(toImmutableSet())).get(0);
    Board newBoard = board.choose(firstChoice);
    assertEquals(4, newBoard.availableChoices().collect(toImmutableSet()).size());
    assertEquals(1, newBoard.length());
    assertFalse(newBoard.availableChoices().collect(toImmutableSet()).contains(firstChoice));
  }

  @Test
  public void choose() {
    Choice firstChoice = board.availableChoices().iterator().next();
    Board newBoard = board.choose(firstChoice);
    assertEquals(ImmutableList.of(firstChoice), newBoard.choicesMade().collect(toImmutableList()));
  }

  @Test
  public void exploration() {
    ImmutableSet<Board> futures = board.explore().collect(toImmutableSet());
    assertTrue(futures.contains(board.choose(board.availableChoices().findFirst().get())));
    assertEquals(326, futures.size());

    Stream<Board> unitScoreBoards = board.exploreWhile(board1 -> board1.score() < 10);
    assertEquals(1, unitScoreBoards.collect(toImmutableSet()).size());

    ImmutableSet<Board> impossibleFutures = board.exploreWhile(board1 -> false)
        .collect(toImmutableSet());
    assertEquals(0, impossibleFutures.size());
  }

  @Test
  public void completenessAndScoring() {
    Board currentBoard = board;
    ImmutableSet<Board> futures = board.explore().collect(toImmutableSet());
    assertFalse(currentBoard.isComplete());
    assertTrue(currentBoard.canProceed());
    assertEquals(42 * 5, currentBoard.boardScore(), 0.01);
    assertEquals(0, currentBoard.score(), 0.01);

    while (currentBoard.availableChoices().findAny().isPresent()) {
      assertTrue(currentBoard.canProceed());
      assertFalse(currentBoard.isComplete());
      currentBoard = currentBoard.choose(currentBoard.availableChoices().iterator().next());
      assertTrue(futures.contains(currentBoard));
    }

    assertTrue(currentBoard.isComplete());
    assertFalse(currentBoard.canProceed());
    assertEquals(0, currentBoard.boardScore(), 0.01);
    assertEquals(42 * 5, currentBoard.score(), 0.01);
    assertEquals(5, currentBoard.length());
  }

  @Test
  public void unmetDemands() {
    assertEquals(
        ImmutableSet.copyOf(board.pendingDemands().collect(toImmutableSet())),
        ImmutableSet.copyOf(demands));
    Choice firstChoice = board.availableChoices().iterator().next();
    assertTrue(board.pendingDemands().collect(toImmutableSet()).contains(firstChoice.demand()));
    Board newBoard = board.choose(firstChoice);
    assertFalse(newBoard.pendingDemands().collect(toImmutableSet()).contains(firstChoice.demand()));
  }

  @Test
  public void viableDemands() {
    Board partiallyImpossibleBoard = board.expand(Set.of(ConstantDemand.create("IMPOSSIBLE")));
    assertEquals(demands.size() + 1,
        partiallyImpossibleBoard.pendingDemands().collect(toImmutableSet()).size());
    assertEquals(demands.size(),
        partiallyImpossibleBoard.viableDemands().collect(toImmutableSet()).size());
    assertEquals(Set.of(ConstantDemand.create("IMPOSSIBLE")),
        Sets.difference(partiallyImpossibleBoard.pendingDemands().collect(toImmutableSet()),
            partiallyImpossibleBoard.viableDemands().collect(toImmutableSet())));
  }

  @Test
  public void history() {
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
