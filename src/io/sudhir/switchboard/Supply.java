package io.sudhir.switchboard;

import com.google.common.collect.ImmutableList;
import java.util.Optional;

public interface Supply {

  Optional<Choice> estimateFor(Demand demand, ImmutableList<Choice> commitments);
}
