package io.sudhir.switchboard;

import io.sudhir.switchboard.boards.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum TestBoards {
  CONSTANT,
  RANDOM;

  public Board board() {
    List<Supply> supplies;
    List<Demand> demands;
    switch (this) {
      case CONSTANT:
        Collection<String> types = Arrays.asList("a", "b", "c", "d", "e");
        supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
        break;

      case RANDOM:
        supplies = new ArrayList<>();
        demands = new ArrayList<>();
        for (Integer i = 1; i < 10; i++) {
          supplies.add(ReducingRandomSupply.create(i.toString()));
        }
        for (Integer i = 1; i < 100; i++) {
          demands.add(ConstantDemand.create(i.toString()));
        }
        break;

      default:
        types = Arrays.asList("a", "b", "c", "d", "e");
        supplies = types.stream().map(ConstantSupply::create).collect(Collectors.toList());
        demands = types.stream().map(ConstantDemand::create).collect(Collectors.toList());
        break;
    }
    return Board.create(supplies, demands);
  }
}
