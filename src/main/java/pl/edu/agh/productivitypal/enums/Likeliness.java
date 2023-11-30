package pl.edu.agh.productivitypal.enums;

public enum Likeliness {
    HATE(1),
    DISLIKE(2),
    NEUTRAL(3),
    LIKE(4),
    LOVE(5);

    private final int value;

    Likeliness(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
