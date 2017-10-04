package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface Board {
  static Board create(Collection<Supply> supplies, Collection<Demand> demands) {
    return ImmutableBoard.create(supplies, demands);
  }

  Board choose(Choice choice);

  boolean isComplete();

  boolean canProceed();

  Stream<Choice> availableChoices();

  Stream<Choice> availableChoices(Demand demand);

  Stream<Demand> pendingDemands();

  List<Board> history();

  List<Choice> choicesMade();

  int score();

  int boardScore();
}
