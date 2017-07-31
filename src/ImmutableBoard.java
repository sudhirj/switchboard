import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableBoard<S extends Supply<D>, D extends Demand> implements Board<Supply<D>, D> {

    private final Collection<S> supplies;
    private final Collection<D> demands;

    ImmutableBoard(Collection<S> supplies, Collection<D> demands) {
        this.supplies = ImmutableList.copyOf(supplies);
        this.demands = ImmutableList.copyOf(demands);
    }


    @Override
    public Board<Supply<D>, D> choose(Choice<Supply<D>, D> choice) {
        return null;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Choice<Supply<D>, D>> availableChoices() {
        return null;
    }

    @Override
    public Collection<D> unmetDemands() {
        return ImmutableSet.copyOf(demands);
    }

    @Override
    public Table<Supply<D>, D, Choice<Supply<D>, D>> matrix() {
        ImmutableTable.Builder<Supply<D>, D, Choice<Supply<D>, D>> builder = ImmutableTable.builder();
        for (S supply : supplies) {
            for (D demand : demands) {
                Choice<Supply<D>, D> choice = supply.estimateFor(demand, null);
                if (choice != null) {
                    builder.put(supply, demand, choice);
                }

            }
        }
        return builder.build();
    }

    @Override
    public List<Table<Supply<D>, D, Choice<Supply<D>, D>>> previousMatrices() {
        return null;
    }
}
