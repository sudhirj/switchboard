package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Choice<T> {

  static <T> Choice create(Supply supply, Demand demand, double score,
      @Nullable T choiceComponents) {
    return new AutoValue_ImmutableChoice<>(supply, demand, score, choiceComponents);
  }

  Supply supply();

  Demand demand();

  double score();

  T choiceAttributes();
}

@AutoValue
abstract class ImmutableChoice<T> implements Choice<T> {

  public abstract Supply supply();

  public abstract Demand demand();

  public abstract double score();

  @Nullable
  public abstract T choiceAttributes();
}
