public interface Player<S extends Supply<D>, D extends Demand> {
    void play(Board<S, D> board);
}
