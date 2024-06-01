package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;

import java.util.Random;

public class WaterFactory {
    public Water createFrozenWater() {
        Water newWater = new Water();

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Temperature(-100));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }

    public Water createBoiledWater() {
        Water newWater = new Water();

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Temperature(100));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }

    public Water createDistilledWater() {
        Water newWater = new Water();

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Salt(0));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }

    public Water createSeawater() {
        Water newWater = new Water();

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Salt(100));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }

    public Water createRandomizedWater() {
        Random random = new Random();

        Water newWater = new Water();

        PropertyContainer propertyContainer = newWater.getPropertyContainer();
        propertyContainer.addProperty(new Salt(random.nextDouble(1000)));
        propertyContainer.addProperty(new Temperature(random.nextDouble(140) - 40));
        newWater.setPropertyContainer(propertyContainer);
        return newWater;
    }
}
