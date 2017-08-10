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
