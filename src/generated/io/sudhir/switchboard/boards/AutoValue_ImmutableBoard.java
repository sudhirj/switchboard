
package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Optional;
import java.util.Set;

 final class AutoValue_ImmutableBoard extends ImmutableBoard {

  private final Set<Supply> supplies;
  private final Set<Demand> demands;
  private final Optional<Choice> choice;
  private final Optional<ImmutableBoard> board;

  AutoValue_ImmutableBoard(
      Set<Supply> supplies,
      Set<Demand> demands,
      Optional<Choice> choice,
      Optional<ImmutableBoard> board) {
    if (supplies == null) {
      throw new NullPointerException("Null supplies");
    }
    this.supplies = supplies;
    if (demands == null) {
      throw new NullPointerException("Null demands");
    }
    this.demands = demands;
    if (choice == null) {
      throw new NullPointerException("Null choice");
    }
    this.choice = choice;
    if (board == null) {
      throw new NullPointerException("Null board");
    }
    this.board = board;
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
  Optional<Choice> choice() {
    return choice;
  }

  @Override
  Optional<ImmutableBoard> board() {
    return board;
  }

  @Override
  public String toString() {
    return "ImmutableBoard{"
        + "supplies=" + supplies + ", "
        + "demands=" + demands + ", "
        + "choice=" + choice + ", "
        + "board=" + board
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
           && (this.choice.equals(that.choice()))
           && (this.board.equals(that.board()));
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
    h ^= this.choice.hashCode();
    h *= 1000003;
    h ^= this.board.hashCode();
    return h;
  }

}
