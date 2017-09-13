package io.sudhir.switchboard;

import io.sudhir.switchboard.boards.Board;

import java.util.Comparator;
import java.util.function.Function;

enum Goals implements Goal {
  MAXIMIZE((i) -> i),
  MINIMIZE((i) -> i * -1);

  private final Comparator<Board> boardComparator;
  private final Comparator<Choice> choiceComparator;
  private final Comparator<Integer> comparator;

  Goals(Function<Integer, Integer> transformer) {
    this.boardComparator = Comparator.comparingInt(board -> transformer.apply(board.score()));
    this.choiceComparator = Comparator.comparingInt(choice -> transformer.apply(choice.score()));
    this.comparator = Comparator.comparingInt(transformer::apply);
  }

  @Override
  public final Comparator<Board> boardComparator() {
    return boardComparator;
  }

  @Override
  public final Comparator<Choice> choiceComparator() {
    return choiceComparator;
  }

  @Override
  public final Comparator<Integer> comparator() {
    return comparator;
  }
}

public interface Goal {
  static Goal MAXIMIZE() {
    return Goals.MAXIMIZE;
  }

  static Goal MINIMIZE() {
    return Goals.MINIMIZE;
  }

  Comparator<Board> boardComparator();

  Comparator<Choice> choiceComparator();

  Comparator<Integer> comparator();
}
