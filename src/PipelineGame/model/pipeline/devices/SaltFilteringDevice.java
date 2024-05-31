package PipelineGame.model.pipeline.devices;

import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.properties.Salt;

public class SaltFilteringDevice implements WaterDevice {
    @Override
    public Water conductWater(Water water) {
        Water newWater = water.clone();
        newWater.addProperty(new Salt(0));
        return newWater;
    }

    @Override
    public String toString() {
        return "Фильтр солей";
    }
}
