import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.devices.RefrigerationDevice;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Temperature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefrigerationDeviceTest {

    @Test
    void sampleTest() {
        RefrigerationDevice heatingDevice = new RefrigerationDevice();
        Water boiledWater = new WaterFactory().createBoiledWater();

        Water resultWater = heatingDevice.conductWater(boiledWater);

        assertNotEquals(resultWater, boiledWater);
        assertNotSame(resultWater, boiledWater);

        Temperature temp = (Temperature) resultWater.getPropertyContainer().getProperty(Temperature.class);
        assertEquals(50, temp.getDegrees());
    }

    @Test
    void conductFrozenWater() {
        RefrigerationDevice heatingDevice = new RefrigerationDevice();
        Water frozenWater = new WaterFactory().createFrozenWater();

        Water resultWater = heatingDevice.conductWater(frozenWater);

        assertNotSame(resultWater, frozenWater);

        Temperature temp = (Temperature) resultWater.getPropertyContainer().getProperty(Temperature.class);
        assertEquals(-100, temp.getDegrees());
    }
}