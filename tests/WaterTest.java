import model.pipeline.Water;
import model.utils.Direction;
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
    void mix_sampleTest() {
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
    void testEquals() {
        // not implemented yet
    }
}