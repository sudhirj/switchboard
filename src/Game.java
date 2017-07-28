import java.util.Collection;

public interface Game<S extends Supply, D extends Demand> {
    Game<S, D> choose(Choice<S, D> player);

    boolean isFinished();

    Collection<Choice<S, D>> choices();

    Collection<D> unmetDemands();

    Collection<Game<S, D>> previousStates();
}
