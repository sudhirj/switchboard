import java.util.Collection;

enum RecheckStrategy {
    ALWAYS, NEVER, AFTER_USAGE
}

public interface Supply<D extends Demand> {
    Choice<Supply<D>, D> estimateFor(D demand, Collection<D> commitments);

    RecheckStrategy recheckStrategy();
}