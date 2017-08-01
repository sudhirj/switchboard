import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        supplies = types.stream().map(ConstantSupply::new).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::new).collect(Collectors.toList());
        board = new ImmutableBoard<>(supplies, demands);
    }

    @Test
    public void availableChoices() throws Exception {
        assertEquals(5, board.availableChoices().size());
    }

    @Test
    public void choose() throws Exception {
        Choice<Supply<ConstantDemand>, ConstantDemand> firstChoice = board.availableChoices().iterator().next();
        Board<Supply<ConstantDemand>, ConstantDemand> newBoard = board.choose(firstChoice);
        assertEquals(ImmutableList.of(firstChoice), ImmutableList.copyOf(newBoard.choicesMade()));
    }

    @Test
    public void isFinished() throws Exception {
        assertFalse(board.isFinished());
    }


    @Test
    public void unmetDemands() throws Exception {
        assertEquals(ImmutableSet.copyOf(board.unmetDemands()), ImmutableSet.copyOf(demands));
        Choice<Supply<ConstantDemand>, ConstantDemand> firstChoice = board.availableChoices().iterator().next();
        assertTrue(board.unmetDemands().contains(firstChoice.demand()));
        Board<Supply<ConstantDemand>, ConstantDemand> newBoard = board.choose(firstChoice);
        assertFalse(newBoard.unmetDemands().contains(firstChoice.demand()));
    }

    @Test
    public void matrix() throws Exception {
        assertNull(board.matrix().get(new ConstantSupply("a"), new ConstantDemand("b")));
        assertNull(board.matrix().get(new ConstantSupply("a"), new ConstantDemand("d")));
        assertEquals(42, board.matrix().get(new ConstantSupply("a"), new ConstantDemand("a")).score());
        assertEquals(42, board.matrix().get(new ConstantSupply("b"), new ConstantDemand("b")).score());
    }

    @Test
    public void previousMatrices() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}