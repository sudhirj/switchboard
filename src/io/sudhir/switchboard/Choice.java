package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;

public interface Choice {

  static Choice create(Supply supply, Demand demand, int score) {
    return new AutoValue_ImmutableChoice(supply, demand, score);
  }

  Supply supply();

  Demand demand();

  double score();
}

@AutoValue
abstract class ImmutableChoice implements Choice {

  public abstract Supply supply();

  public abstract Demand demand();

  public abstract double score();
}
