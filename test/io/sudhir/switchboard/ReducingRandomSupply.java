package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class ReducingRandomSupply implements Supply {
  public static ReducingRandomSupply create(String type) {
    return new AutoValue_ReducingRandomSupply(type);
  }

  @Override
  public Choice estimateFor(Demand demand, List<Choice> commitments) {
    return Choice.create(
        this,
        demand,
        commitments.size() > 0
            ? Double.valueOf(Math.random() * 10).intValue()
            : 50 + Double.valueOf(Math.random() * 1000).intValue());
  }

  abstract String type();
}
