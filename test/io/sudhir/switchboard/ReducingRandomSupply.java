package io.sudhir.switchboard;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.auto.value.AutoValue;
import java.util.Optional;
import java.util.stream.Stream;

@AutoValue
public abstract class ReducingRandomSupply implements Supply {

  public static ReducingRandomSupply create(String type) {
    return new AutoValue_ReducingRandomSupply(type);
  }

  @Override
  public Optional<Choice> estimateFor(Demand demand, Stream<Choice> commitments) {
    return Optional.of(
        Choice.create(
            this,
            demand,
            commitments.filter(c -> c.supply().equals(this)).collect(toImmutableList()).size() > 0
                ? 42
                : 50));
  }

  abstract String type();
}
