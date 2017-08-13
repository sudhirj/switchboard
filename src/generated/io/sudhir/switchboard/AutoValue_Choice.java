
package io.sudhir.switchboard;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Choice extends Choice {

  private final Supply supply;
  private final Demand demand;
  private final int score;

  AutoValue_Choice(
      Supply supply,
      Demand demand,
      int score) {
    if (supply == null) {
      throw new NullPointerException("Null supply");
    }
    this.supply = supply;
    if (demand == null) {
      throw new NullPointerException("Null demand");
    }
    this.demand = demand;
    this.score = score;
  }

  @Override
  public Supply supply() {
    return supply;
  }

  @Override
  public Demand demand() {
    return demand;
  }

  @Override
  public int score() {
    return score;
  }

  @Override
  public String toString() {
    return "Choice{"
        + "supply=" + supply + ", "
        + "demand=" + demand + ", "
        + "score=" + score
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Choice) {
      Choice that = (Choice) o;
      return (this.supply.equals(that.supply()))
           && (this.demand.equals(that.demand()))
           && (this.score == that.score());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.supply.hashCode();
    h *= 1000003;
    h ^= this.demand.hashCode();
    h *= 1000003;
    h ^= this.score;
    return h;
  }

}
