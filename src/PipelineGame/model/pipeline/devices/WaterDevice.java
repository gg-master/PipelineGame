package PipelineGame.model.pipeline.devices;

import PipelineGame.model.pipeline.water.Water;

public interface WaterDevice {
    Water conductWater(Water water);

    String toString();
}
