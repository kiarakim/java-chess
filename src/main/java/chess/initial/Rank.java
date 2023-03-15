package chess.initial;

public enum Rank {

    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1);

    private final int rank;

    Rank(final int rank) {
        this.rank = rank;
    }

    public int value() {
        return rank;
    }
}
