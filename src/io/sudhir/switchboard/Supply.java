package io.sudhir.switchboard;

import java.util.List;

enum RecheckStrategy {
    NEVER, ON_COMMITTMENT
}

public interface Supply {

    Choice estimateFor(Demand demand, List<Choice> commitments);

    RecheckStrategy recheckStrategy();
}