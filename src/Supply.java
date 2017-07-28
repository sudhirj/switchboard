import java.util.Set;

public interface Supply<D extends Demand> {
    Choice estimateFor(D demand, Set<D> commitments);
}
