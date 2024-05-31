package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;

import java.util.Random;

public class WaterFactory {
    public Water createFrozenWater() {
        Water newWater = new Water();
        newWater.addProperty(new Temperature(-100));
        return newWater;
    }

    public Water createBoiledWater() {
        Water newWater = new Water();
        newWater.addProperty(new Temperature(100));
        return newWater;
    }

    public Water createDistilledWater() {
        Water newWater = new Water();
        newWater.addProperty(new Salt(0));
        return newWater;
    }

    public Water createSeawater() {
        Water newWater = new Water();
        newWater.addProperty(new Salt(100));
        return newWater;
    }

    public Water createRandomizedWater() {
        Random random = new Random();

        Water newWater = new Water();
        newWater.addProperty(new Salt(random.nextDouble(1000)));
        newWater.addProperty(new Temperature(random.nextDouble(200) - 60));

        return newWater;
    }
}
