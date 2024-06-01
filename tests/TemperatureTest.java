import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureTest {

    @Test
    void sampleTest() {
        Temperature temperature = new Temperature();
        Temperature otherTemperature = new Temperature(100);
        Temperature mixedTemp = (Temperature) temperature.mix(otherTemperature);

        assertEquals(15, temperature.getDegrees());
        assertEquals(100, otherTemperature.getDegrees());
        assertEquals(57.5, mixedTemp.getDegrees());

        assertNotSame(temperature, mixedTemp);
        assertNotSame(otherTemperature, mixedTemp);
    }

    @Test
    void mixingDifferentClasses() {
        Temperature temperature = new Temperature();
        Salt nonTemperature = new Salt();

        assertThrows(IllegalArgumentException.class, () -> temperature.mix(nonTemperature));
    }

    @Test
    void mixingTemperatureBelowZero() {
        Temperature temperature = new Temperature(-50);
        Temperature otherTemperature = new Temperature(-100);
        Temperature mixedTemp = (Temperature) temperature.mix(otherTemperature);

        assertEquals(-75, mixedTemp.getDegrees());
    }

    @Test
    void mixingSameTemperature() {
        Temperature temperature = new Temperature(-100);
        Temperature mixedTemp = (Temperature) temperature.mix(temperature);

        assertEquals(-100, mixedTemp.getDegrees());
    }

    @Test
    void creatingTemperatureWithValueBeyondLimits() {
        Temperature temperature = new Temperature(1000);
        assertEquals(100, temperature.getDegrees());

        temperature = new Temperature(-1000);
        assertEquals(-100, temperature.getDegrees());

        temperature = new Temperature(101);
        assertEquals(100, temperature.getDegrees());

        temperature = new Temperature(-101);
        assertEquals(-100, temperature.getDegrees());
    }

    @Test
    void equals_sampleTest() {
        Temperature temperature = new Temperature(45);
        Temperature otherTemperature = new Temperature(45);
        Temperature differentTemperature = new Temperature(22);

        assertEquals(temperature, otherTemperature);
        assertNotEquals(temperature, differentTemperature);
    }

    @Test
    void equals_differentClasses() {
        Temperature temperature = new Temperature(45);
        Object obj = new Object();

        assertNotEquals(temperature, obj);
    }
}