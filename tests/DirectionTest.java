import model.utils.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void clockwise() {
        assertEquals(Direction.north(), Direction.west().clockwise());
        assertEquals(Direction.east(), Direction.north().clockwise());
        assertEquals(Direction.south(), Direction.east().clockwise());
        assertEquals(Direction.west(), Direction.south().clockwise());
    }

    @Test
    void opposite() {
        assertEquals(Direction.east(), Direction.west().opposite());
        assertEquals(Direction.west(), Direction.east().opposite());

        assertEquals(Direction.north(), Direction.south().opposite());
        assertEquals(Direction.south(), Direction.north().opposite());

        assertNotEquals(Direction.north(), Direction.north().opposite());
        assertNotEquals(Direction.north(), Direction.west().opposite());
        assertNotEquals(Direction.north(), Direction.east().opposite());
    }

    @Test
    void isOpposite() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertTrue(direction_north.isOpposite(direction_south));
        assertTrue(direction_south.isOpposite(direction_north));

        assertTrue(direction_east.isOpposite(direction_west));
        assertTrue(direction_west.isOpposite(direction_east));

        assertFalse(direction_north.isOpposite(direction_north));

        assertFalse(direction_north.isOpposite(direction_west));
        assertFalse(direction_west.isOpposite(direction_north));

        assertFalse(direction_north.isOpposite(direction_east));
        assertFalse(direction_east.isOpposite(direction_north));

        assertFalse(direction_south.isOpposite(direction_south));

        assertFalse(direction_south.isOpposite(direction_east));
        assertFalse(direction_east.isOpposite(direction_south));

        assertFalse(direction_south.isOpposite(direction_west));
        assertFalse(direction_west.isOpposite(direction_south));

        assertFalse(direction_west.isOpposite(direction_west));
        assertFalse(direction_east.isOpposite(direction_east));
    }

    @Test
    void testEquals() {
        Direction direction_north = Direction.north();
        Direction direction_south = Direction.south();
        Direction direction_east = Direction.east();
        Direction direction_west = Direction.west();

        assertEquals(direction_north, direction_north);
        assertEquals(direction_north.hashCode(), direction_north.hashCode());

        assertNotEquals(direction_north, direction_east);
        assertNotEquals(direction_north.hashCode(), direction_east.hashCode());

        assertNotEquals(direction_north, direction_south);
        assertNotEquals(direction_north.hashCode(), direction_south.hashCode());

        assertNotEquals(direction_north, direction_west);
        assertNotEquals(direction_north.hashCode(), direction_west.hashCode());

        assertNotEquals(direction_south, direction_east);
        assertNotEquals(direction_south.hashCode(), direction_east.hashCode());

        assertNotEquals(direction_south, direction_west);
        assertNotEquals(direction_south.hashCode(), direction_west.hashCode());

        assertNotEquals(direction_east, direction_west);
        assertNotEquals(direction_east.hashCode(), direction_west.hashCode());
    }
}