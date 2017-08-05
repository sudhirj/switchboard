import java.util.List;

enum RecheckStrategy {
    ALWAYS, NEVER, AFTER_USAGE
}

public interface Supply<D extends Demand> {
    Choice<Supply<D>, D> estimateFor(D demand, List<D> commitments);

    RecheckStrategy recheckStrategy();
}