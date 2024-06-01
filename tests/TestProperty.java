import PipelineGame.model.pipeline.water.PropertyContainer;
import PipelineGame.model.pipeline.water.WaterProperty;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PropertyContainerTest {

    @Test
    void sampleTest() {
        PropertyContainer propertyContainer = new PropertyContainer();

        Temperature temperature = new Temperature(45);
        propertyContainer.addProperty(temperature);

        HashMap<Class<? extends  WaterProperty>, WaterProperty> properties = propertyContainer.getProperties();
        Temperature resTemperature = (Temperature) propertyContainer.getProperty(Temperature.class);

        assertTrue(properties.containsKey(Temperature.class));
        assertEquals(temperature, properties.get(Temperature.class));
        assertEquals(temperature, resTemperature);
    }

    @Test
    void getProperties_sampleTest() {
        PropertyContainer propertyContainer = new PropertyContainer();

        Temperature temperature = new Temperature(45);
        propertyContainer.addProperty(temperature);

        HashMap<Class<? extends  WaterProperty>, WaterProperty> properties = propertyContainer.getProperties();
        assertNotSame(properties, propertyContainer.getProperties());
    }

    @Test
    void getProperty_doesntNotHaveNewProperty() {
        TestProperty testProperty = new TestProperty();
        PropertyContainer propertyContainer = new PropertyContainer();

        WaterProperty resProperty = propertyContainer.getProperty(testProperty.getClass());

        assertNotSame(testProperty, resProperty);
        assertSame(testProperty.getClass(), resProperty.getClass());
        assertEquals(10, testProperty.value);
    }

    @Test
    void mixProperties_newPropertyInRight() {
        PropertyContainer left = new PropertyContainer();
        left.addProperty(new Salt(1000));

        PropertyContainer right = new PropertyContainer();
        right.addProperty(new TestProperty());

        PropertyContainer mixedContainer = left.mixProperties(right);
        Set<Class<? extends WaterProperty>> propertyClasses = mixedContainer.getProperties().keySet();

        assertEquals(3, mixedContainer.getProperties().size());
        assertNotSame(left, mixedContainer);
        assertNotSame(right, mixedContainer);

        for (Class<? extends WaterProperty> cls : left.getProperties().keySet()) {
            assertTrue(propertyClasses.contains(cls));
        }
        for (Class<? extends WaterProperty> cls : right.getProperties().keySet()) {
            assertTrue(propertyClasses.contains(cls));
        }
    }

    @Test
    void mixProperties_newPropertyInLeft() {
        PropertyContainer left = new PropertyContainer();
        left.addProperty(new Salt(1000));
        left.addProperty(new TestProperty());

        PropertyContainer right = new PropertyContainer();

        PropertyContainer mixedContainer = left.mixProperties(right);
        Set<Class<? extends WaterProperty>> propertyClasses = mixedContainer.getProperties().keySet();

        assertEquals(3, mixedContainer.getProperties().size());
        assertNotSame(left, mixedContainer);
        assertNotSame(right, mixedContainer);

        for (Class<? extends WaterProperty> cls : left.getProperties().keySet()) {
            assertTrue(propertyClasses.contains(cls));
        }
        for (Class<? extends WaterProperty> cls : right.getProperties().keySet()) {
            assertTrue(propertyClasses.contains(cls));
        }
    }

    @Test
    void equals_sampleTest() {
        PropertyContainer propertyContainer = new PropertyContainer();
        PropertyContainer samePropertyContainer = new PropertyContainer();

        PropertyContainer differentContainer = new PropertyContainer();
        differentContainer.addProperty(new TestProperty());
        differentContainer.addProperty(new Salt(1000));

        assertEquals(propertyContainer, samePropertyContainer);
        assertNotEquals(propertyContainer, differentContainer);
    }

    @Test
    void equals_differentClasses() {
        PropertyContainer propertyContainer = new PropertyContainer();
        Object obj = new Object();

        assertNotEquals(propertyContainer, obj);
    }

    @Test
    void cloneTest() {
        PropertyContainer container = new PropertyContainer();
        container.addProperty(new TestProperty());

        PropertyContainer cloned = container.clone();

        assertNotSame(container, cloned);
        assertEquals(container, cloned);

        container.addProperty(new Salt(1000));
        assertNotEquals(container, cloned);
    }
}

public class TestProperty extends WaterProperty {
    public int value = 1;

    public TestProperty() {
        value = 10;
    }

    @Override
    public WaterProperty mix(WaterProperty other) {
        return new TestProperty();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof TestProperty;
    }
}