import com.google.auto.value.AutoValue;

@AutoValue
abstract class ConstantChoice<S extends Supply<D>, D extends Demand> implements Choice<S, D> {

    static <S extends Supply<D>, D extends Demand> ConstantChoice<S, D> create(S supply, D demand, int score) {
        return new AutoValue_ConstantChoice<>(supply, demand, score);
    }

    @Override
    abstract public S supply();

    @Override
    abstract public D demand();

    @Override
    abstract public int score();
}
