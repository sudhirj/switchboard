package io.sudhir.switchboard;

import io.sudhir.switchboard.boards.ImmutableBoard;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum TestBoards {
    CONSTANT, RANDOM;

    public Board board() {
        List<Supply> supplies;
        List<Demand> demands;
        switch (this) {
            case RANDOM:
                Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
                supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
                demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
                break;

            case CONSTANT:
                types = Arrays.asList("a", "b", "c", "d", "e");
                supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
                demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
                break;

            default:
                types = Arrays.asList("a", "b", "c", "d", "e");
                supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
                demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
                break;
        }
        return new ImmutableBoard(supplies, demands);
    }
}
