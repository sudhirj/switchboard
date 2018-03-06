
package io.sudhir.switchboard;

import org.checkerframework.checker.nullness.qual.Nullable;

 final class AutoValue_ImmutableChoice<T> extends ImmutableChoice<T> {

  private final Supply supply;
  private final Demand demand;
  private final double score;
  private final T choiceAttributes;

  AutoValue_ImmutableChoice(
      Supply supply,
      Demand demand,
      double score,
      T choiceAttributes) {
    if (supply == null) {
      throw new NullPointerException("Null supply");
    }
    this.supply = supply;
    if (demand == null) {
      throw new NullPointerException("Null demand");
    }
    this.demand = demand;
    this.score = score;
    this.choiceAttributes = choiceAttributes;
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
  public T choiceAttributes() {
    return choiceAttributes;
  }

  @Override
  public String toString() {
    return "ImmutableChoice{"
        + "supply=" + supply + ", "
        + "demand=" + demand + ", "
        + "score=" + score + ", "
        + "choiceAttributes=" + choiceAttributes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ImmutableChoice) {
      ImmutableChoice<?> that = (ImmutableChoice<?>) o;
      return (this.supply.equals(that.supply()))
           && (this.demand.equals(that.demand()))
           && (Double.doubleToLongBits(this.score) == Double.doubleToLongBits(that.score()))
           && ((this.choiceAttributes == null) ? (that.choiceAttributes() == null) : this.choiceAttributes.equals(that.choiceAttributes()));
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
    h *= 1000003;
    h ^= (choiceAttributes == null) ? 0 : this.choiceAttributes.hashCode();
    return h;
  }

}
