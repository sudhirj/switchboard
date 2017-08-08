import javax.annotation.Nullable;
import java.util.List;

enum RecheckStrategy {
    NEVER, ON_COMMITTMENT
}

public interface Supply<D extends Demand> {
    @Nullable
    Choice<Supply<D>, D> estimateFor(D demand, List<Choice<Supply<D>, D>> commitments);

    RecheckStrategy recheckStrategy();
}