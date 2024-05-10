import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.utils.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void clockwise_sampleTest() {
        assertEquals(Direction.north(), Direction.west().clockwise());
        assertEquals(Direction.east(), Direction.north().clockwise());
        assertEquals(Direction.south(), Direction.east().clockwise());
        assertEquals(Direction.west(), Direction.south().clockwise());
    }

    @Test
    void opposite_sampleTest() {
        assertEquals(Direction.east(), Direction.west().opposite());
        assertEquals(Direction.west(), Direction.east().opposite());

        assertEquals(Direction.north(), Direction.south().opposite());
        assertEquals(Direction.south(), Direction.north().opposite());

        assertNotEquals(Direction.north(), Direction.north().opposite());
        assertNotEquals(Direction.north(), Direction.west().opposite());
        assertNotEquals(Direction.north(), Direction.east().opposite());
    }

    @Test
    void isOpposite_northDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertTrue(direction_north.isOpposite(direction_south));
        assertTrue(direction_south.isOpposite(direction_north));

        assertFalse(direction_north.isOpposite(direction_north));
        assertFalse(direction_north.isOpposite(direction_west));
        assertFalse(direction_north.isOpposite(direction_east));
    }

    @Test
    void isOpposite_southDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertTrue(direction_south.isOpposite(direction_north));
        assertTrue(direction_north.isOpposite(direction_south));

        assertFalse(direction_south.isOpposite(direction_south));
        assertFalse(direction_south.isOpposite(direction_west));
        assertFalse(direction_south.isOpposite(direction_east));
    }

    @Test
    void isOpposite_westDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertTrue(direction_west.isOpposite(direction_east));
        assertTrue(direction_east.isOpposite(direction_west));

        assertFalse(direction_west.isOpposite(direction_west));
        assertFalse(direction_west.isOpposite(direction_south));
        assertFalse(direction_west.isOpposite(direction_north));
    }

    @Test
    void isOpposite_eastDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertTrue(direction_west.isOpposite(direction_east));
        assertTrue(direction_east.isOpposite(direction_west));

        assertFalse(direction_east.isOpposite(direction_east));
        assertFalse(direction_east.isOpposite(direction_south));
        assertFalse(direction_east.isOpposite(direction_north));
    }

    @Test
    void testEquals_northDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertEquals(direction_north, Direction.north());
        assertEquals(direction_north.hashCode(), Direction.north().hashCode());

        assertNotEquals(direction_north, direction_east);
        assertNotEquals(direction_north.hashCode(), direction_east.hashCode());

        assertNotEquals(direction_north, direction_south);
        assertNotEquals(direction_north.hashCode(), direction_south.hashCode());

        assertNotEquals(direction_north, direction_west);
        assertNotEquals(direction_north.hashCode(), direction_west.hashCode());
    }

    @Test
    void testEquals_southDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertEquals(direction_south, Direction.south());
        assertEquals(direction_south.hashCode(), Direction.south().hashCode());

        assertNotEquals(direction_south, direction_east);
        assertNotEquals(direction_south.hashCode(), direction_east.hashCode());

        assertNotEquals(direction_south, direction_north);
        assertNotEquals(direction_south.hashCode(), direction_north.hashCode());

        assertNotEquals(direction_south, direction_west);
        assertNotEquals(direction_south.hashCode(), direction_west.hashCode());
    }

    @Test
    void testEquals_westDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertEquals(direction_west, Direction.west());
        assertEquals(direction_west.hashCode(), Direction.west().hashCode());

        assertNotEquals(direction_west, direction_east);
        assertNotEquals(direction_west.hashCode(), direction_east.hashCode());

        assertNotEquals(direction_west, direction_north);
        assertNotEquals(direction_west.hashCode(), direction_north.hashCode());

        assertNotEquals(direction_west, direction_south);
        assertNotEquals(direction_west.hashCode(), direction_south.hashCode());
    }

    @Test
    void testEquals_eastDirection() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertEquals(direction_east, Direction.east());
        assertEquals(direction_east.hashCode(), Direction.east().hashCode());

        assertNotEquals(direction_east, direction_west);
        assertNotEquals(direction_east.hashCode(), direction_west.hashCode());

        assertNotEquals(direction_east, direction_north);
        assertNotEquals(direction_east.hashCode(), direction_north.hashCode());

        assertNotEquals(direction_east, direction_south);
        assertNotEquals(direction_west.hashCode(), direction_south.hashCode());
    }

    @Test
    void testEquals_invalidSecondObject() {
        Direction direction_north = Direction.north();
        assertNotEquals(null, direction_north);
        assertNotEquals(new Tap(), direction_north);
    }
}