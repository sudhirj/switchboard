import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImmutableGameTest {
    private List<ConstantDemand> demands;
    private List<ConstantSupply<ConstantDemand>> supplies;
    private ImmutableGame game;

    @Before
    public void setUp() throws Exception {
        Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
        supplies = types.stream().map(ConstantSupply::new).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::new).collect(Collectors.toList());
        game = new ImmutableGame(supplies, demands);
    }

    @Test
    public void availableChoices() throws Exception {

    }

    @Test
    public void choose() throws Exception {

    }

    @Test
    public void isFinished() throws Exception {
    }


    @Test
    public void unmetDemands() throws Exception {
    }

    @Test
    public void matrix() throws Exception {
    }

    @Test
    public void previousMatrices() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


}