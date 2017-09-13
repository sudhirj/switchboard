package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;

import java.util.Collection;
import java.util.List;

public interface Board {
  static Board create(Collection<Supply> supplies, Collection<Demand> demands) {
    return ImmutableBoard.create(supplies, demands);
  }

  Board choose(Choice choice);

  boolean isComplete();

  boolean canProceed();

  Collection<Choice> availableChoices();

  Collection<Demand> pendingDemands();

  List<Board> history();

  List<Choice> choicesMade();

  int score();

  int boardScore();
}
