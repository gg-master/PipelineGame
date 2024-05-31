package PipelineGame.model.pipeline.devices;

import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.properties.Temperature;

public class RefrigerationDevice implements WaterDevice {

    @Override
    public Water conductWater(Water water) {
        Water newWater = water.clone();
        Temperature temperature = (Temperature) newWater.getProperty(Temperature.class);

        double temperatureDelta = 50;
        newWater.addProperty(new Temperature(temperature.getDegrees() - temperatureDelta));
        return newWater;
    }

    @Override
    public String toString() {
        return "Охлаждатель";
    }
}
