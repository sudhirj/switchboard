
package io.sudhir.switchboard.boards;

import io.sudhir.switchboard.Choice;
import io.sudhir.switchboard.Demand;
import io.sudhir.switchboard.Supply;
import java.util.Optional;
import java.util.Set;

final class AutoValue_ImmutableBoard extends ImmutableBoard {

  private final Set<Supply> supplies;
  private final Set<Demand> demands;
  private final Optional<Board> board;
  private final Optional<Choice> choice;

  AutoValue_ImmutableBoard(
      Set<Supply> supplies,
      Set<Demand> demands,
      Optional<Board> board,
      Optional<Choice> choice) {
    if (supplies == null) {
      throw new NullPointerException("Null supplies");
    }
    this.supplies = supplies;
    if (demands == null) {
      throw new NullPointerException("Null demands");
    }
    this.demands = demands;
    if (board == null) {
      throw new NullPointerException("Null board");
    }
    this.board = board;
    if (choice == null) {
      throw new NullPointerException("Null choice");
    }
    this.choice = choice;
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
  public Optional<Board> board() {
    return board;
  }

  @Override
  public Optional<Choice> choice() {
    return choice;
  }

  @Override
  public String toString() {
    return "ImmutableBoard{"
        + "supplies=" + supplies + ", "
        + "demands=" + demands + ", "
        + "board=" + board + ", "
        + "choice=" + choice
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
          && (this.board.equals(that.board()))
          && (this.choice.equals(that.choice()));
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
    h ^= this.board.hashCode();
    h *= 1000003;
    h ^= this.choice.hashCode();
    return h;
  }

}
