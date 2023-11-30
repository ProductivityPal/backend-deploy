package pl.edu.agh.productivitypal.enums;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXTRA_HARD(4);

    private final int value;
    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
