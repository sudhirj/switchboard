package io.sudhir.switchboard;

import java.util.Collection;
import java.util.List;

public interface Board {
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
