package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;
import java.util.HashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Choice {

  static Choice create(Supply supply, Demand demand, double score,
      @Nullable HashMap<String, Double> scoreComponents) {
    return new AutoValue_ImmutableChoice(supply, demand, score, scoreComponents);
  }

  Supply supply();

  Demand demand();

  double score();

  HashMap<String, Double> scoreComponents();
}

@AutoValue
abstract class ImmutableChoice implements Choice {

  public abstract Supply supply();

  public abstract Demand demand();

  public abstract double score();

  @Nullable
  public abstract HashMap<String, Double> scoreComponents();
}
