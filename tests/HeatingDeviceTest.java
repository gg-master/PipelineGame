import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Temperature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeatingDeviceTest {
    @Test
    void sampleTest() {
        HeatingDevice heatingDevice = new HeatingDevice();
        Water frozenWater = new WaterFactory().createFrozenWater();

        Water resultWater = heatingDevice.conductWater(frozenWater);

        assertNotEquals(resultWater, frozenWater);
        assertNotSame(resultWater, frozenWater);

        Temperature temp = (Temperature) resultWater.getPropertyContainer().getProperty(Temperature.class);
        assertEquals(-50, temp.getDegrees());
    }

    @Test
    void conductBoiledWater() {
        HeatingDevice heatingDevice = new HeatingDevice();
        Water boiledWater = new WaterFactory().createBoiledWater();

        Water resultWater = heatingDevice.conductWater(boiledWater);

        assertNotSame(resultWater, boiledWater);

        Temperature temp = (Temperature) resultWater.getPropertyContainer().getProperty(Temperature.class);
        assertEquals(100, temp.getDegrees());
    }
}