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
    public void availableChoices() throws Exception {
        assertEquals(5, board.availableChoices().size());
        Choice firstChoice = ImmutableList.copyOf(board.availableChoices()).get(0);
        Board newBoard = board.choose(firstChoice);
        assertEquals(4, newBoard.availableChoices().size());
        assertFalse(newBoard.availableChoices().contains(firstChoice));
    }

    @Test
    public void choose() throws Exception {
        Choice firstChoice = board.availableChoices().iterator().next();
        Board newBoard = board.choose(firstChoice);
        assertEquals(ImmutableList.of(firstChoice), ImmutableList.copyOf(newBoard.choicesMade()));
    }

    @Test
    public void completenessAndScoring() throws Exception {
        Board currentBoard = board;
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
        Choice firstChoice = board.availableChoices().iterator().next();
        assertTrue(board.pendingDemands().contains(firstChoice.demand()));
        Board newBoard = board.choose(firstChoice);
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
        List<List<Choice>> recordedCommitments = new ArrayList<>();
        Supply recordingSupply = new Supply() {
            @Override
            public Choice estimateFor(Demand demand, List<Choice> commitments) {
                recordedCommitments.add(commitments);
                return Choice.create(this, demand, 42);
            }

            @Override
            public RecheckStrategy recheckStrategy() {
                return null;
            }
        };
        List<Demand> demands = ImmutableList.of(ConstantDemand.create("a"), ConstantDemand.create("b"), ConstantDemand.create("c"));
        Board recordingBoard = new ImmutableBoard(ImmutableList.of(recordingSupply), demands);
        assertEquals(3, recordedCommitments.size());
        assertTrue(recordedCommitments.get(0).isEmpty());
        Choice choice = recordingBoard.availableChoices().iterator().next();
        recordingBoard.choose(choice);
        assertEquals(6, recordedCommitments.size());
        assertEquals(1, recordedCommitments.get(3).size());
        assertEquals(choice, recordedCommitments.get(3).get(0));
    }

    @Test
    public void history() throws Exception {
        Board firstBoard = board;
        Board secondBoard = firstBoard.choose(firstBoard.availableChoices().iterator().next());
        assertEquals(ImmutableList.of(firstBoard), ImmutableList.copyOf(secondBoard.history()));
        Board thirdBoard = secondBoard.choose(secondBoard.availableChoices().iterator().next());
        assertEquals(ImmutableList.of(firstBoard, secondBoard), ImmutableList.copyOf(thirdBoard.history()));
    }
}