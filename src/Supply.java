import java.util.List;

enum RecheckStrategy {
    NEVER, ON_COMMITTMENT
}

interface Supply {

    Choice estimateFor(Demand demand, List<Choice> commitments);

    RecheckStrategy recheckStrategy();
}