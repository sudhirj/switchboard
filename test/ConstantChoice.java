public class ConstantChoice<S extends Supply<D>, D extends Demand> implements Choice<Supply<D>, D> {
    private final S supply;
    private final D demand;
    private final int score;

    ConstantChoice(S supply, D demand, int score) {
        this.supply = supply;
        this.demand = demand;
        this.score = score;
    }

    @Override
    public int score() {
        return this.score;
    }

    @Override
    public S supply() {
        return this.supply;
    }

    @Override
    public D demand() {
        return this.demand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantChoice)) return false;

        ConstantChoice that = (ConstantChoice) o;

        return score == that.score;
    }

    @Override
    public int hashCode() {
        return score;
    }
}
