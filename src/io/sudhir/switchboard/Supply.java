package io.sudhir.switchboard;

import java.util.List;
import java.util.Optional;

public interface Supply {

  Optional<Choice> estimateFor(Demand demand, List<Choice> commitments);
}
