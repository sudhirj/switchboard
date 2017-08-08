import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ImmutableBoardTest {
    private List<ConstantDemand> demands;
    private List<ConstantSupply> supplies;
    private ImmutableBoard<ConstantSupply, ConstantDemand> board;

    @Before
    public void setUp() throws Exception {
        Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
        supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
        board = new ImmutableBoard<>(supplies, demands);
    }

    @Test
    public void availableChoices() throws Exception {
        assertEquals(5, board.availableChoices().size());
        Choice<Supply<ConstantDemand>, ConstantDemand> firstChoice = ImmutableList.copyOf(board.availableChoices()).get(0);
        Board<Supply<ConstantDemand>, ConstantDemand> newBoard = board.choose(firstChoice);
        assertEquals(4, newBoard.availableChoices().size());
        assertFalse(newBoard.availableChoices().contains(firstChoice));
    }

    @Test
    public void choose() throws Exception {
        Choice<Supply<ConstantDemand>, ConstantDemand> firstChoice = board.availableChoices().iterator().next();
        Board<Supply<ConstantDemand>, ConstantDemand> newBoard = board.choose(firstChoice);
        assertEquals(ImmutableList.of(firstChoice), ImmutableList.copyOf(newBoard.choicesMade()));
    }

    @Test
    public void completenessAndScoring() throws Exception {
        Board<Supply<ConstantDemand>, ConstantDemand> currentBoard = board;
        assertFalse(currentBoard.isComplete());
        assertTrue(currentBoard.canProceed());
        assertEquals(42 * 5, currentBoard.boardScore());
        assertEquals(0, currentBoard.score());

        while (!currentBoard.availableChoices().isEmpty()) {
            assertTrue(currentBoard.canProceed());
            assertFalse(currentBoard.isComplete());
            currentBoard = currentBoard.choose(currentBoard.availableChoices().iterator().next());
        }

        assertTrue(currentBoard.isComplete());
        assertFalse(currentBoard.canProceed());
        assertEquals(0, currentBoard.boardScore());
        assertEquals(42 * 5, currentBoard.score());
    }

    @Test
    public void unmetDemands() throws Exception {
        assertEquals(ImmutableSet.copyOf(board.pendingDemands()), ImmutableSet.copyOf(demands));
        Choice<Supply<ConstantDemand>, ConstantDemand> firstChoice = board.availableChoices().iterator().next();
        assertTrue(board.pendingDemands().contains(firstChoice.demand()));
        Board<Supply<ConstantDemand>, ConstantDemand> newBoard = board.choose(firstChoice);
        assertFalse(newBoard.pendingDemands().contains(firstChoice.demand()));
    }

    @Test
    public void matrix() throws Exception {
        assertNull(board.matrix().get(ConstantSupply.create("a"), ConstantDemand.create("b")));
        assertNull(board.matrix().get(ConstantSupply.create("a"), ConstantDemand.create("d")));
        assertEquals(42, board.matrix().get(ConstantSupply.create("a"), ConstantDemand.create("a")).score());
        assertEquals(42, board.matrix().get(ConstantSupply.create("b"), ConstantDemand.create("b")).score());
    }

    @Test
    public void matrixRebuilding() throws Exception {
        List<List<Choice<Supply<ConstantDemand>, ConstantDemand>>> recordedCommitments = new ArrayList<>();
        Supply<ConstantDemand> recordingSupply = new Supply<ConstantDemand>() {
            @Override
            public Choice<Supply<ConstantDemand>, ConstantDemand> estimateFor(ConstantDemand demand, List<Choice<Supply<ConstantDemand>, ConstantDemand>> commitments) {
                recordedCommitments.add(commitments);
                return ConstantChoice.create(this, demand, 42);
            }

            @Override
            public RecheckStrategy recheckStrategy() {
                return null;
            }
        };
        List<ConstantDemand> demands = ImmutableList.of(ConstantDemand.create("a"), ConstantDemand.create("b"), ConstantDemand.create("c"));
        Board<Supply<ConstantDemand>, ConstantDemand> recordingBoard = new ImmutableBoard<>(ImmutableList.of(recordingSupply), demands);
        assertEquals(3, recordedCommitments.size());
        assertTrue(recordedCommitments.get(0).isEmpty());
        Choice<Supply<ConstantDemand>, ConstantDemand> choice = recordingBoard.availableChoices().iterator().next();
        recordingBoard.choose(choice);
        assertEquals(6, recordedCommitments.size());
        assertEquals(1, recordedCommitments.get(3).size());
        assertEquals(choice, recordedCommitments.get(3).get(0));
    }

    @Test
    public void history() throws Exception {
        Board<Supply<ConstantDemand>, ConstantDemand> firstBoard = board;
        Board<Supply<ConstantDemand>, ConstantDemand> secondBoard = firstBoard.choose(firstBoard.availableChoices().iterator().next());
        assertEquals(ImmutableList.of(firstBoard), ImmutableList.copyOf(secondBoard.history()));
        Board<Supply<ConstantDemand>, ConstantDemand> thirdBoard = secondBoard.choose(secondBoard.availableChoices().iterator().next());
        assertEquals(ImmutableList.of(firstBoard, secondBoard), ImmutableList.copyOf(thirdBoard.history()));
    }
}