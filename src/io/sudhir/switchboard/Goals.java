package io.sudhir.switchboard;


import java.util.Comparator;
import java.util.function.Function;

public enum Goals implements Goal {
    MINIMIZE((i) -> i * -1), MAXIMIZE((i) -> i);

    private final Comparator<Board> boardComparator;
    private final Comparator<Choice> choiceComparator;
    private final Comparator<Integer> comparator;

    Goals(Function<Integer, Integer> transformer) {
        this.boardComparator = Comparator.comparingInt(board -> transformer.apply(board.score()));
        this.choiceComparator = Comparator.comparingInt(choice -> transformer.apply(choice.score()));
        this.comparator = Comparator.comparingInt(transformer::apply);
    }

    @Override
    public Comparator<Board> boardComparator() {
        return boardComparator;
    }

    @Override
    public Comparator<Choice> choiceComparator() {
        return choiceComparator;
    }

    @Override
    public Comparator<Integer> comparator() {
        return comparator;
    }
}
