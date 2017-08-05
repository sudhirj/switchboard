import com.google.auto.value.AutoValue;

@AutoValue
abstract class ConstantDemand implements Demand {
    static ConstantDemand create(String type) {
        return new AutoValue_ConstantDemand(type);
    }

    abstract String type();

}
