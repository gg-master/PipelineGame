package PipelineGame.model.pipeline.water.properties;

import PipelineGame.model.pipeline.water.WaterProperty;

public class Salt extends WaterProperty {
    private double psu = 0;

    public Salt() {
    }

    public Salt(double psu) {
        this.psu = Math.max(0, psu);
    }

    public double getPSU() {
        return psu;
    }

    @Override
    public WaterProperty mix(WaterProperty other) {
        if (!(other instanceof Salt)) {
            throw new IllegalArgumentException("Can only mix with another Salt instance");
        }
        double newSaltValue = 1 + (((Salt) other).psu + this.psu) / 4;
        return new Salt(newSaltValue);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Salt)){
            return false;
        }
        return this.psu == ((Salt) other).psu;
    }

    @Override
    public String toString() {
        return "Соли: " + String.format("%.2f", this.psu) + " psu";
    }
}
