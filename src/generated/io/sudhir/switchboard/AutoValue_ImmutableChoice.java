
package io.sudhir.switchboard;

import java.util.HashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

 final class AutoValue_ImmutableChoice extends ImmutableChoice {

  private final Supply supply;
  private final Demand demand;
  private final double score;
  private final @Nullable HashMap<String, Double> scoreComponents;

  AutoValue_ImmutableChoice(
      Supply supply,
      Demand demand,
      double score,
      @Nullable HashMap<String, Double> scoreComponents) {
    if (supply == null) {
      throw new NullPointerException("Null supply");
    }
    this.supply = supply;
    if (demand == null) {
      throw new NullPointerException("Null demand");
    }
    this.demand = demand;
    this.score = score;
    this.scoreComponents = scoreComponents;
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
  public @Nullable HashMap<String, Double> scoreComponents() {
    return scoreComponents;
  }

  @Override
  public String toString() {
    return "ImmutableChoice{"
        + "supply=" + supply + ", "
        + "demand=" + demand + ", "
        + "score=" + score + ", "
        + "scoreComponents=" + scoreComponents
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
           && (Double.doubleToLongBits(this.score) == Double.doubleToLongBits(that.score()))
           && ((this.scoreComponents == null) ? (that.scoreComponents() == null) : this.scoreComponents.equals(that.scoreComponents()));
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
    h ^= (scoreComponents == null) ? 0 : this.scoreComponents.hashCode();
    return h;
  }

}
