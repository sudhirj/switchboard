

import java.util.List;
import java.util.Objects;

public class ConstantSupply<CD extends ConstantDemand> implements Supply<ConstantDemand> {
    private final String type;

    ConstantSupply(String type) {
        this.type = type;
    }

    @Override
    public Choice<ConstantSupply<ConstantDemand>, ConstantDemand> estimateFor(ConstantDemand demand, List<ConstantDemand> commitments) {
        if (Objects.equals(this.type, demand.type)) {
            return () -> 42;
        }
        return null;
    }

    @Override
    public RecheckStrategy recheckStrategy() {
        return null;
    }
}
