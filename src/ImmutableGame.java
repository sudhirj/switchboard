import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableGame<S extends Supply<D>, D extends Demand> implements Game<Supply<Demand>, Demand> {

    private final Collection<Supply<Demand>> supplies;
    private final Collection<Demand> demands;

    ImmutableGame(Collection<Supply<Demand>> supplies, Collection<Demand> demands) {
        this.supplies = ImmutableList.copyOf(supplies);
        this.demands = ImmutableList.copyOf(demands);
    }

    @Override
    public Game<Supply<Demand>, Demand> choose(Choice<Supply<Demand>, Demand> choice) {
        return null;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Choice<Supply<Demand>, Demand>> availableChoices() {
        return null;
    }

    @Override
    public Collection<Demand> unmetDemands() {
        return null;
    }

    @Override
    public Table<Supply<Demand>, Demand, Choice<Supply<Demand>, Demand>> matrix() {
        return null;
    }

    @Override
    public List<Table<Supply<Demand>, Demand, Choice<Supply<Demand>, Demand>>> previousMatrices() {
        return null;
    }
}
