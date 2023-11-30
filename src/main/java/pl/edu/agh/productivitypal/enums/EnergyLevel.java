package pl.edu.agh.productivitypal.enums;

public enum EnergyLevel {
    LOW(0.5),
    MEDIUM(1.0),
    HIGH(1.5);

    private final double value;

    EnergyLevel(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
