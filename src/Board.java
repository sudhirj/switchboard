import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;

public interface Board<S extends Supply<D>, D extends Demand> {
    Board<S, D> choose(Choice<S, D> choice);

    boolean isFinished();

    Collection<Choice<S, D>> availableChoices();

    Collection<D> unmetDemands();

    Table<S, D, Choice<S, D>> matrix();

    List<Board<S, D>> history();

    List<Choice<S, D>> choicesMade();
}
