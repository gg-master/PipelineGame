import PipelineGame.model.Cell;
import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.devices.SaltFilteringDevice;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.model.pipeline.water.PropertyContainer;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.segments.pipes.Adapter;
import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.pipeline.segments.pipes.Cross;
import PipelineGame.model.pipeline.segments.pipes.Tee;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.model.utils.Direction;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    @Test
    void setCell_sampleTest() {
        Cell cell_first = new Cell();

        Tap tap = new Tap();

        tap.setCell(cell_first);
        assertEquals(cell_first, tap.getCell());
        assertEquals(cell_first.getSegment(), tap);
    }

    @Test
    void setCell_testingMutualActionsWithMultipleCells() {
        Cell cell_first = new Cell();
        Cell cell_second = new Cell();

        Tap tap = new Tap();

        tap.setCell(null);
        assertNull(tap.getCell());
        assertNull(cell_first.getSegment());

        tap.setCell(cell_second);
        assertEquals(cell_second, tap.getCell());
        assertEquals(cell_second.getSegment(), tap);
    }

    @Test
    void getNeighborSegment_testingMutualActions() {
        Cell cell_first = new Cell();
        Cell cell_second = new Cell();
        cell_first.setNeighbor(Direction.north(), cell_second);

        Tap tap = new Tap();
        Hatch hatch = new Hatch();

        assertNull(tap.getNeighborSegment(Direction.north()));
        assertNull(hatch.getNeighborSegment(Direction.south()));

        tap.setCell(cell_first);
        hatch.setCell(cell_second);

        assertEquals(hatch, tap.getNeighborSegment(Direction.north()));
        assertEquals(tap, hatch.getNeighborSegment(Direction.south()));
        assertNull(tap.getNeighborSegment(Direction.east()));

        tap.setCell(null);
        assertNull(hatch.getNeighborSegment(Direction.east()));

        tap.setCell(cell_second);
        assertNull(hatch.getCell());
    }

    @Test
    void getAvailableDirections_tapAndHatchDirections() {
        Tap tap = new Tap();
        Hatch hatch = new Hatch();

        HashSet<Direction> expTapDirections = new HashSet<>() {{
           add(Direction.east());
        }};
        HashSet<Direction> tapDirections = tap.getAvailableDirections();
        assertEquals(expTapDirections, tapDirections);

        HashSet<Direction> expHatchDirections = new HashSet<>() {{
            add(Direction.east());
        }};
        HashSet<Direction> hatchDirections = hatch.getAvailableDirections();
        assertEquals(expHatchDirections, hatchDirections);
    }

    @Test
    void getAvailableDirections_adapterDirections() {
        Adapter adapter = new Adapter();
        HashSet<Direction> expAdapterDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.east());
        }};
        HashSet<Direction> adapterDirections = adapter.getAvailableDirections();
        assertEquals(expAdapterDirections, adapterDirections);
    }

    @Test
    void getAvailableDirections_cornerDirections() {
        Corner corner = new Corner();
        HashSet<Direction> expCornerDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.north());
        }};
        HashSet<Direction> cornerDirections = corner.getAvailableDirections();
        assertEquals(expCornerDirections, cornerDirections);
    }
    @Test
    void getAvailableDirections_teeDirections() {
        Tee tee = new Tee();
        HashSet<Direction> expTeeDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.north());
            add(Direction.east());
        }};
        HashSet<Direction> teeDirections = tee.getAvailableDirections();
        assertEquals(expTeeDirections, teeDirections);
    }

    @Test
    void getAvailableDirections_crossDirections() {
        Cross cross = new Cross();
        HashSet<Direction> expCrossDirections = new HashSet<>() {{
            add(Direction.west()); add(Direction.north());
            add(Direction.east()); add(Direction.south());
        }};
        HashSet<Direction> crossDirections = cross.getAvailableDirections();
        assertEquals(expCrossDirections, crossDirections);
    }

    @Test
    void conductWater_sampleTest() {
        Water initWater = new Water();
        Adapter adapter = new Adapter();

        Water resWater = adapter.conductWater(initWater);

        assertNotSame(initWater, resWater);
        assertEquals(initWater, resWater);

        PropertyContainer propertyContainer = new PropertyContainer();
        propertyContainer.addProperty(new Salt(1000));
        initWater.setPropertyContainer(propertyContainer);

        assertNotEquals(initWater, resWater);
        assertNotEquals(initWater, adapter.getWater());
    }

    @Test
    void conductWater_sampleMixTest() {
        Water initWater = new Water();
        Adapter adapter = new Adapter();
        adapter.conductWater(initWater);

        Water newWater = new Water();
        Water resWater = adapter.conductWater(newWater);

        assertNotSame(initWater, resWater);
        assertNotSame(newWater, resWater);

        assertNotEquals(initWater, resWater);
        assertNotEquals(newWater, resWater);

        PropertyContainer propertyContainer = new PropertyContainer();
        propertyContainer.addProperty(new Salt(1000));
        resWater.setPropertyContainer(propertyContainer);

        assertNotEquals(adapter.getWater(), resWater);
    }

    @Test
    void conductWater_deviceTest() {
        PropertyContainer propertyContainer = new PropertyContainer();
        propertyContainer.addProperty(new Temperature(-100));
        propertyContainer.addProperty(new Salt(1000));

        Water frozenWater = new Water(propertyContainer);

        Adapter adapter = new Adapter();
        adapter.addDevice(new HeatingDevice());
        adapter.addDevice(new HeatingDevice());
        adapter.addDevice(new SaltFilteringDevice());

        Water conductedWater = adapter.conductWater(frozenWater);

        Temperature waterTemp = (Temperature) conductedWater.getPropertyContainer().getProperty(Temperature.class);
        Salt salt = (Salt) conductedWater.getPropertyContainer().getProperty(Salt.class);

        assertTrue(((Temperature) frozenWater.getPropertyContainer().getProperty(Temperature.class)).isFrozen());

        assertEquals(0, waterTemp.getDegrees());
        assertEquals(0, salt.getPSU());

        assertNotSame(frozenWater, conductedWater);
    }

    @Test
    void rotateRight_sampleTest() {
        Tap tap = new Tap();
        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.south());
        }};

        tap.rotateRight();

        assertEquals(expDirections, tap.getAvailableDirections());
    }

    @Test
    void rotateRight_crossRotate() {
        Cross cross = new Cross();
        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.south());
            add(Direction.north());
            add(Direction.west());
            add(Direction.east());
        }};

        cross.rotateRight();

        assertEquals(expDirections, cross.getAvailableDirections());
    }

    @Test
    void rotateRight_adapterMultipleRotate() {
        Adapter adapter = new Adapter();
        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.south());
            add(Direction.north());
        }};

        adapter.rotateRight();
        assertEquals(expDirections, adapter.getAvailableDirections());

        expDirections = new HashSet<>() {{
            add(Direction.west());
            add(Direction.east());
        }};

        adapter.rotateRight();
        assertEquals(expDirections, adapter.getAvailableDirections());
    }

    @Test
    void addingDevicesTest() {
        Adapter adapter = new Adapter();

        List<WaterDevice> devices = adapter.getDevices();
        assertEquals(0, devices.size());

        HeatingDevice heatingDevice = new HeatingDevice();
        adapter.addDevice(heatingDevice);

        assertNotEquals(devices, adapter.getDevices());
        assertNotSame(devices, adapter.getDevices());

        assertTrue(adapter.getDevices().contains(heatingDevice));
    }
}