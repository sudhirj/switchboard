import java.util.Collection;
import java.util.Objects;

public class ConstantSupply implements Supply<ConstantDemand> {
    private final String type;

    ConstantSupply(String type) {
        this.type = type;
    }

    @Override
    public Choice<Supply<ConstantDemand>, ConstantDemand> estimateFor(ConstantDemand demand, Collection<ConstantDemand> commitments) {
        if (Objects.equals(this.type, demand.type)) {
            return () -> 42;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantSupply)) return false;

        ConstantSupply that = (ConstantSupply) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ConstantSupply{" +
                "type='" + type + '\'' +
                '}';
    }


    @Override
    public RecheckStrategy recheckStrategy() {
        return null;
    }
}
