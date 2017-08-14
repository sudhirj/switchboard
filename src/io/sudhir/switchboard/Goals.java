package io.sudhir.switchboard;


import java.util.Comparator;
import java.util.function.Function;

public enum Goals implements Goal {
    MINIMIZE((i) -> i * -1), MAXIMIZE((i) -> i);

    private final Function<Integer, Integer> transformer;

    Goals(Function<Integer, Integer> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Comparator<Board> boardComparator() {
        return Comparator.comparingInt(board -> transformer.apply(board.score()));
    }

    @Override
    public Comparator<Choice> choiceComparator() {
        return Comparator.comparingInt(choice -> transformer.apply(choice.score()));
    }

    @Override
    public Comparator<Integer> comparator() {
        return Comparator.comparingInt(transformer::apply);
    }


}
