package PipelineGame.model.pipeline.water.properties;

import PipelineGame.model.pipeline.water.WaterProperty;

public class Temperature extends WaterProperty {
    private final double degrees;

    public Temperature() {
        this(15);
    }

    public Temperature(double degrees) {
        this.degrees = Math.min(100.0, Math.max(-100.0, degrees));
    }

    public double getDegrees() {
        return degrees;
    }

    public boolean isFrozen() {
        return this.degrees < -41;
    }

    @Override
    public WaterProperty mix(WaterProperty other) {
        if (!(other instanceof Temperature)) {
            throw new IllegalArgumentException("Can only mix with another Temperature instance");
        }
        double newTemperatureValue = (((Temperature) other).degrees + this.degrees) / 2;
        return new Temperature(newTemperatureValue);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Temperature)){
            return false;
        }
        return this.degrees == ((Temperature) other).degrees;
    }

    @Override
    public String toString() {
        return "Температура: " + String.format("%.2f", this.degrees) + " ⁰C";
    }
}
