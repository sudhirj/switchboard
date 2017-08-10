package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Objects;

@AutoValue
abstract class ConstantSupply implements Supply {
    static ConstantSupply create(String type) {
        return new AutoValue_ConstantSupply(type);
    }

    @Override
    public Choice estimateFor(Demand demand, List<Choice> commitments) {
        // Avoiding the casts on supply nodes for their companion demand nodes is far more trouble than it's worth.
        // https://blogs.msdn.microsoft.com/ericlippert/2011/02/03/curiouser-and-curiouser/
        ConstantDemand constantDemand = (ConstantDemand) demand;
        if (Objects.equals(type(), constantDemand.type())) {
            return Choice.create(this, demand, 42);
        }
        return null;
    }

    @Override
    public RecheckStrategy recheckStrategy() {
        return null;
    }

    abstract String type();
}
