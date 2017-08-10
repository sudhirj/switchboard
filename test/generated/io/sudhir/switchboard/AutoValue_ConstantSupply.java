
package io.sudhir.switchboard;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_ConstantSupply extends ConstantSupply {

    private final String type;

    AutoValue_ConstantSupply(
            String type) {
        if (type == null) {
            throw new NullPointerException("Null type");
        }
        this.type = type;
    }

    @Override
    String type() {
        return type;
    }

    @Override
    public String toString() {
        return "ConstantSupply{"
                + "type=" + type
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ConstantSupply) {
            ConstantSupply that = (ConstantSupply) o;
            return (this.type.equals(that.type()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.type.hashCode();
        return h;
    }

}
