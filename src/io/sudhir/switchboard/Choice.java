package io.sudhir.switchboard;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Choice {

    static Choice create(Supply supply, Demand demand, int score) {
        return new AutoValue_Choice(supply, demand, score);
    }

    public abstract Supply supply();

    public abstract Demand demand();

    public abstract int score();
}
