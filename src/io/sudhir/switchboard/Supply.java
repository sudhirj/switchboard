package io.sudhir.switchboard;

import java.util.List;

public interface Supply {

    Choice estimateFor(Demand demand, List<Choice> commitments);

}