package io.sudhir.switchboard;

import java.util.Optional;
import java.util.stream.Stream;

public interface Supply {

  Optional<Choice> estimateFor(Demand demand, Stream<Choice> commitments);
}
