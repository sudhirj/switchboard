package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AutoValue
public abstract class ConstantSupply implements Supply {

  public static ConstantSupply create(String type) {
    return new AutoValue_ConstantSupply(type);
  }

  @Override
  public Optional<Choice> estimateFor(Demand demand, ImmutableList<Choice> commitments) {
    // Avoiding the casts on supply nodes for their companion demand nodes is far more trouble than
    // it's worth.
    // https://blogs.msdn.microsoft.com/ericlippert/2011/02/03/curiouser-and-curiouser/
    if (demand instanceof ConstantDemand) {
      ConstantDemand constantDemand = (ConstantDemand) demand;
      if (Objects.equals(type(), constantDemand.type())) {
        return Optional.of(Choice.create(this, demand, 42, null));
      }
    }
    return Optional.empty();
  }

  abstract String type();
}
