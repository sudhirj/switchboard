
package io.sudhir.switchboard;

 final class AutoValue_ImmutableChoice extends ImmutableChoice {

  private final Supply supply;
  private final Demand demand;
  private final double score;

  AutoValue_ImmutableChoice(
      Supply supply,
      Demand demand,
      double score) {
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
  public double score() {
    return score;
  }

  @Override
  public String toString() {
    return "ImmutableChoice{"
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
    if (o instanceof ImmutableChoice) {
      ImmutableChoice that = (ImmutableChoice) o;
      return (this.supply.equals(that.supply()))
           && (this.demand.equals(that.demand()))
           && (Double.doubleToLongBits(this.score) == Double.doubleToLongBits(that.score()));
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
    h ^= (int) ((Double.doubleToLongBits(this.score) >>> 32) ^ Double.doubleToLongBits(this.score));
    return h;
  }

}
