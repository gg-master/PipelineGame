import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaltTest {

    @Test
    void sampleTest() {
        Salt salt = new Salt();
        Salt otherSalt = new Salt(100);
        Salt mixedSalt = (Salt) salt.mix(otherSalt);

        assertEquals(0, salt.getPSU());
        assertEquals(100, otherSalt.getPSU());
        assertEquals(26, mixedSalt.getPSU());

        assertNotSame(salt, mixedSalt);
        assertNotSame(otherSalt, mixedSalt);
    }

    @Test
    void mixingDifferentClasses() {
        Salt salt = new Salt();
        Temperature nonSalt = new Temperature();

        assertThrows(IllegalArgumentException.class, () -> salt.mix(nonSalt));
    }

    @Test
    void mixingSameSalt() {
        Salt salt = new Salt(100);
        Salt mixedSalt = (Salt) salt.mix(salt);

        assertEquals(51, mixedSalt.getPSU());
    }

    @Test
    void creatingTemperatureWithValueBeyondLimits() {
        Salt salt = new Salt(-50);
        assertEquals(0, salt.getPSU());

        salt = new Salt(-1);
        assertEquals(0, salt.getPSU());
    }

    @Test
    void equals_sampleTest() {
        Salt salt = new Salt(45);
        Salt otherSalt = new Salt(45);
        Salt differentSalt = new Salt(22);

        assertEquals(salt, otherSalt);
        assertNotEquals(salt, differentSalt);
    }

    @Test
    void equals_differentClasses() {
        Salt salt = new Salt(45);
        Object obj = new Object();

        assertNotEquals(salt, obj);
    }
}