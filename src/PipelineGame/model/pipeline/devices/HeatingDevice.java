package PipelineGame.model.pipeline.devices;

import PipelineGame.model.pipeline.water.PropertyContainer;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.properties.Temperature;

public class HeatingDevice implements WaterDevice {
    @Override
    public Water conductWater(Water water) {
        Water newWater = water.clone();
        Temperature temperature = (Temperature) newWater.getPropertyContainer().getProperty(Temperature.class);

        double temperatureDelta = 50;

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Temperature(temperature.getDegrees() + temperatureDelta));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }

    @Override
    public String toString() {
        return "Нагреватель";
    }
}
