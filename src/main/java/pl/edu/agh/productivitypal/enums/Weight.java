package pl.edu.agh.productivitypal.enums;

public enum Weight {
    DEADLINE(2),
    DIFFICULTY(3),
    TIME_ESTIMATE(1),
    LIKELINESS(2);

    private final int value;

    Weight(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
