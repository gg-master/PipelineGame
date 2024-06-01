import PipelineGame.model.pipeline.devices.SaltFilteringDevice;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Salt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaltFilteringDeviceTest {

    @Test
    void sampleTest() {
        SaltFilteringDevice saltFilteringDevice = new SaltFilteringDevice();
        Water seawater = new WaterFactory().createSeawater();

        Water resultWater = saltFilteringDevice.conductWater(seawater);

        assertNotEquals(resultWater, seawater);
        assertNotSame(resultWater, seawater);

        Salt salt = (Salt) resultWater.getPropertyContainer().getProperty(Salt.class);
        assertEquals(0, salt.getPSU());
    }

    @Test
    void conductDistilledWater() {
        SaltFilteringDevice saltFilteringDevice = new SaltFilteringDevice();
        Water distilledWater = new WaterFactory().createDistilledWater();

        Water resultWater = saltFilteringDevice.conductWater(distilledWater);

        assertNotSame(resultWater, distilledWater);

        Salt salt = (Salt) resultWater.getPropertyContainer().getProperty(Salt.class);
        assertEquals(0, salt.getPSU());
    }
}