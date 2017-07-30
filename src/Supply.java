
import java.util.List;

enum RecheckStrategy {
    ALWAYS, NEVER, AFTER_CHANGES
}

public interface Supply<D extends Demand> {
    Choice estimateFor(D demand, List<D> commitments);

    RecheckStrategy recheckStrategy();
}