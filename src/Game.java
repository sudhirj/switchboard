
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Game<S extends Supply<D>, D extends Demand> {
    Game<S, D> choose(Choice<S, D> choice);

    boolean isFinished();

    Set<Choice<S, D>> availableChoices();

    Collection<D> unmetDemands();

    Table<S, D, Choice<S, D>> matrix();

    List<Table<S, D, Choice<S, D>>> previousMatrices();
}
