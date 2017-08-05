

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_ConstantChoice<S extends Supply<D>, D extends Demand> extends ConstantChoice<S, D> {

    private final S supply;
    private final D demand;
    private final int score;

    AutoValue_ConstantChoice(
            S supply,
            D demand,
            int score) {
        if (supply == null) {
            throw new NullPointerException("Null supply");
        }
        this.supply = supply;
        if (demand == null) {
            throw new NullPointerException("Null demand");
        }
        this.demand = demand;
        this.score = score;
    }

    @Override
    public S supply() {
        return supply;
    }

    @Override
    public D demand() {
        return demand;
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return "ConstantChoice{"
                + "supply=" + supply + ", "
                + "demand=" + demand + ", "
                + "score=" + score
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ConstantChoice) {
            ConstantChoice<?, ?> that = (ConstantChoice<?, ?>) o;
            return (this.supply.equals(that.supply()))
                    && (this.demand.equals(that.demand()))
                    && (this.score == that.score());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.supply.hashCode();
        h *= 1000003;
        h ^= this.demand.hashCode();
        h *= 1000003;
        h ^= this.score;
        return h;
    }

}
