package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.lang.Override;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("com.google.auto.value.extension.memoized.MemoizeExtension")
final class AutoValue_ImmutableBoard extends $AutoValue_ImmutableBoard {
  private volatile Collection<Choice> availableChoices;

  AutoValue_ImmutableBoard(Set<Supply> supplies$, Set<Demand> demands$, List<Choice> choicesMade$,
      List<Board> history$) {
    super(supplies$, demands$, choicesMade$, history$);
  }

  @Override
  public Collection<Choice> availableChoices() {
    if (availableChoices == null) {
      synchronized (this) {
        if (availableChoices == null) {
          availableChoices = super.availableChoices();
          if (availableChoices == null) {
            throw new NullPointerException("availableChoices() cannot return null");
          }
        }
      }
    }
    return availableChoices;
  }
}
