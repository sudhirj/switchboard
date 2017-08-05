import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Objects;

@AutoValue
abstract class ConstantSupply implements Supply<ConstantDemand> {

    static ConstantSupply create(String type) {
        return new AutoValue_ConstantSupply(type);
    }

    @Override
    public Choice<Supply<ConstantDemand>, ConstantDemand> estimateFor(ConstantDemand demand, List<ConstantDemand> commitments) {
        if (Objects.equals(type(), demand.type())) {
            return ConstantChoice.create(this, demand, 42);
        }
        return null;
    }

    @Override
    public RecheckStrategy recheckStrategy() {
        return null;
    }

    abstract String type();
}
