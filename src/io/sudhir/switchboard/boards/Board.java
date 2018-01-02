package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Board {

  static Board create(Collection<? extends Supply> supplies, Collection<? extends Demand> demands) {
    return ImmutableBoard.create(supplies, demands);
  }

  Board choose(Choice choice);

  boolean isComplete();

  boolean canProceed();

  Set<Supply> availableSupplies();

  Stream<Choice> availableChoices();

  Stream<Choice> availableChoices(Demand demand);

  Stream<Demand> pendingDemands();

  Stream<Demand> viableDemands();

  Stream<? extends Board> history();

  Stream<Board> explore();

  Stream<Board> exploreWhile(Predicate<Board> explorer);

  Stream<Choice> choicesMade();

  double score();

  double boardScore();

  long workDone();

  Board expand(Collection<? extends Demand> constantDemands);
}
