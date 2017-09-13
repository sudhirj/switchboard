package io.sudhir.switchboard;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ConstantDemand implements Demand {
  public static ConstantDemand create(String type) {
    return new AutoValue_ConstantDemand(type);
  }

  abstract String type();
}
