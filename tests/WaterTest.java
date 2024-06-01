import PipelineGame.model.pipeline.water.PropertyContainer;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.model.utils.Direction;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class WaterTest {

    @Test
    void getFlowDirections_sampleTest() {
        Water water = new Water();

        HashSet<Direction> availableDirections = new HashSet<>() {{
           add(Direction.north());
           add(Direction.south());
        }};

        HashSet<Direction> resFlowDirections = water.getFlowDirections(availableDirections);
        assertEquals(availableDirections, resFlowDirections);
    }

    @Test
    void getFlowDirections_noOneOfAvailableDirections() {
        Water water = new Water();

        HashSet<Direction> availableDirections = new HashSet<>();

        HashSet<Direction> resFlowDirections = water.getFlowDirections(availableDirections);
        assertEquals(availableDirections, resFlowDirections);
    }

    @Test
    void getFlowDirections_waterRunFromWest() {
        Water water = new Water();
        water = water.runWaterOnDirection(Direction.east());

        HashSet<Direction> availableDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.east());
        }};

        HashSet<Direction> expFlowDirections = new HashSet<>() {{
            add(Direction.east());
        }};

        HashSet<Direction> resFlowDirections = water.getFlowDirections(availableDirections);

        assertEquals(expFlowDirections, resFlowDirections);
    }

    @Test
    void getFlowDirections_waterRunFromWestMixedWithWaterRunFromNorth() {
        Water waterNorth = new Water().runWaterOnDirection(Direction.south());
        Water waterWest = new Water().runWaterOnDirection(Direction.east());

        Water currentWater = waterNorth.mix(waterWest);

        HashSet<Direction> availableDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.north());
        }};

        HashSet<Direction> expFlowDirections = new HashSet<>();

        HashSet<Direction> resFlowDirections = currentWater.getFlowDirections(availableDirections);

        assertEquals(expFlowDirections, resFlowDirections);
    }

    @Test
    void getFlowDirections_frozenWater() {
        Water water = new WaterFactory().createFrozenWater();

        HashSet<Direction> availableDirections = new HashSet<>();

        HashSet<Direction> resFlowDirections = water.getFlowDirections(availableDirections);
        assertEquals(availableDirections, resFlowDirections);
    }

    @Test
    void runWaterOnDirection_sampleTest() {
        Water initWater = new Water();

        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.east());
            add(Direction.north());
            add(Direction.south());
        }};

        HashSet<Direction> flowDirections = new HashSet<>() {{
            add(Direction.east());
            add(Direction.west());
            add(Direction.north());
            add(Direction.south());
        }};

        Water runningWater = initWater.runWaterOnDirection(Direction.east());
        assertEquals(expDirections, runningWater.getFlowDirections(flowDirections));
        assertNotSame(initWater, runningWater);
        assertEquals(initWater, runningWater);
    }

    @Test
    void runWaterOnDirection_noAvailableFlowDirections() {
        Water waterNorth = new Water().runWaterOnDirection(Direction.south());
        Water waterWest = new Water().runWaterOnDirection(Direction.east());
        Water waterEast = new Water().runWaterOnDirection(Direction.west());
        Water waterSouth = new Water().runWaterOnDirection(Direction.north());

        Water currentWater = waterNorth.mix(waterWest).mix(waterEast).mix(waterSouth);

        HashSet<Direction> expDirections = new HashSet<>();

        HashSet<Direction> flowDirections = new HashSet<>() {{
            add(Direction.east());
            add(Direction.west());
            add(Direction.north());
            add(Direction.south());
        }};
        HashSet<Direction> resFlowDirections = currentWater.getFlowDirections(flowDirections);

        assertEquals(expDirections, resFlowDirections);

        Water runningWater = currentWater.runWaterOnDirection(Direction.east());
        assertNull(runningWater);
    }

    @Test
    void mixingProperties() {
        WaterFactory waterFactory = new WaterFactory();
        Water boiledWater = waterFactory.createBoiledWater();
        Water frozenWater = waterFactory.createFrozenWater();

        Water mixed = boiledWater.mix(frozenWater);

        Temperature boiledTemp = (Temperature) boiledWater.getPropertyContainer().getProperty(Temperature.class);
        Temperature frozenTemp = (Temperature) frozenWater.getPropertyContainer().getProperty(Temperature.class);
        Temperature mixedTemp = (Temperature) mixed.getPropertyContainer().getProperty(Temperature.class);

        assertEquals(0, mixedTemp.getDegrees());
        assertEquals(100, boiledTemp.getDegrees());
        assertEquals(-100, frozenTemp.getDegrees());

        assertNotSame(boiledWater, mixed);
        assertNotSame(frozenWater, mixed);
        assertNotSame(mixed.getPropertyContainer(), mixed.getPropertyContainer());
        assertNotSame(boiledWater.getPropertyContainer(), mixed.getPropertyContainer());
        assertNotSame(frozenWater.getPropertyContainer(), mixed.getPropertyContainer());

        assertNotEquals(boiledWater.getPropertyContainer(), mixed.getPropertyContainer());
        assertNotEquals(frozenWater.getPropertyContainer(), mixed.getPropertyContainer());

        assertEquals(mixed.getPropertyContainer(), mixed.getPropertyContainer());
    }

    @Test
    void mixingFlowDirections() {
        Water waterFromNorth = new Water().runWaterOnDirection(Direction.south());
        Water waterFromSouth = new Water().runWaterOnDirection(Direction.north());

        Water currentWater = waterFromNorth.mix(waterFromSouth);

        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.east());
            add(Direction.west());
        }};

        HashSet<Direction> flowDirections = new HashSet<>() {{
            add(Direction.east());
            add(Direction.west());
            add(Direction.north());
            add(Direction.south());
        }};

        HashSet<Direction> resFlowDirections = currentWater.getFlowDirections(flowDirections);
        assertEquals(expDirections, resFlowDirections);
    }

    @Test
    void equals_sampleTest() {
        Water water = new Water();
        Water otherWater = new Water();

        Water differentWater = new Water();
        PropertyContainer propertyContainer = new PropertyContainer();
        propertyContainer.addProperty(new Temperature(-100));
        differentWater.setPropertyContainer(propertyContainer);

        assertEquals(water, otherWater);
        assertNotEquals(water, differentWater);
    }

    @Test
    void equals_differentClasses() {
        Water water = new Water();
        Object obj = new Object();

        assertNotEquals(water, obj);
    }

    @Test
    void cloneTest() {
        Water water = new Water();
        Water cloned = water.clone();

        assertNotSame(water, cloned);
        assertEquals(water, cloned);

        PropertyContainer container = new PropertyContainer();
        container.addProperty(new Salt(1000));
        water.setPropertyContainer(container);

        assertNotEquals(water, cloned);
    }

    @Test
    void setPropertyContainerTest() {
        PropertyContainer container = new PropertyContainer();
        container.addProperty(new Salt(1000));

        Water water = new Water();
        assertNotEquals(water.getPropertyContainer(), container);

        water.setPropertyContainer(container);
        assertEquals(water.getPropertyContainer(), container);

        container.addProperty(new Salt(500));

        assertNotEquals(water.getPropertyContainer(), container);
    }
}