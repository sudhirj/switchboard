
package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Board;
import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;

import javax.annotation.Generated;
import java.util.List;
import java.util.Set;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
abstract class $AutoValue_ImmutableBoard extends ImmutableBoard {

    private final Set<Supply> supplies;
    private final Set<Demand> demands;
    private final List<Choice> choicesMade;
    private final List<Board> history;

    $AutoValue_ImmutableBoard(
            Set<Supply> supplies,
            Set<Demand> demands,
            List<Choice> choicesMade,
            List<Board> history) {
        if (supplies == null) {
            throw new NullPointerException("Null supplies");
        }
        this.supplies = supplies;
        if (demands == null) {
            throw new NullPointerException("Null demands");
        }
        this.demands = demands;
        if (choicesMade == null) {
            throw new NullPointerException("Null choicesMade");
        }
        this.choicesMade = choicesMade;
        if (history == null) {
            throw new NullPointerException("Null history");
        }
        this.history = history;
    }

    @Override
    Set<Supply> supplies() {
        return supplies;
    }

    @Override
    Set<Demand> demands() {
        return demands;
    }

    @Override
    public List<Choice> choicesMade() {
        return choicesMade;
    }

    @Override
    public List<Board> history() {
        return history;
    }

    @Override
    public String toString() {
        return "ImmutableBoard{"
                + "supplies=" + supplies + ", "
                + "demands=" + demands + ", "
                + "choicesMade=" + choicesMade + ", "
                + "history=" + history
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ImmutableBoard) {
            ImmutableBoard that = (ImmutableBoard) o;
            return (this.supplies.equals(that.supplies()))
                    && (this.demands.equals(that.demands()))
                    && (this.choicesMade.equals(that.choicesMade()))
                    && (this.history.equals(that.history()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.supplies.hashCode();
        h *= 1000003;
        h ^= this.demands.hashCode();
        h *= 1000003;
        h ^= this.choicesMade.hashCode();
        h *= 1000003;
        h ^= this.history.hashCode();
        return h;
    }

}
