import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@AutoValue
abstract class ConstantSupply implements Supply<ConstantDemand> {
    static ConstantSupply create(String type) {
        return new AutoValue_ConstantSupply(type);
    }

    @Nullable
    @Override
    public Choice<Supply<ConstantDemand>, ConstantDemand> estimateFor(ConstantDemand demand, List<Choice<Supply<ConstantDemand>, ConstantDemand>> commitments) {
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
