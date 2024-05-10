import PipelineGame.model.Cell;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.utils.Direction;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void getNeighbor_sampleTest() {
        Cell targetCell = new Cell();
        Cell northN = new Cell();
        Cell southN = new Cell();
        Cell westN = new Cell();
        Cell eastN = new Cell();

        targetCell.setNeighbor(Direction.north(), northN);
        targetCell.setNeighbor(Direction.south(), southN);
        targetCell.setNeighbor(Direction.east(), eastN);
        targetCell.setNeighbor(Direction.west(), westN);

        assertEquals(northN, targetCell.getNeighbor(Direction.north()));
        assertEquals(southN, targetCell.getNeighbor(Direction.south()));
        assertEquals(westN, targetCell.getNeighbor(Direction.west()));
        assertEquals(eastN, targetCell.getNeighbor(Direction.east()));

        assertEquals(targetCell, northN.getNeighbor(Direction.north().opposite()));
        assertEquals(targetCell, southN.getNeighbor(Direction.south().opposite()));
        assertEquals(targetCell, westN.getNeighbor(Direction.west().opposite()));
        assertEquals(targetCell, eastN.getNeighbor(Direction.east().opposite()));
    }

    @Test
    void setNeighbor_sampleTest() {
        Cell targetCell = new Cell();
        Cell northN = new Cell();
        Cell southN = new Cell();
        Cell northN_northN = new Cell();
        Cell northN_southN = new Cell();

        targetCell.setNeighbor(Direction.north(), northN);
        targetCell.setNeighbor(Direction.south(), southN);
        northN.setNeighbor(Direction.north(), northN_northN);
        northN.setNeighbor(Direction.south(), northN_southN);

        assertNull(targetCell.getNeighbor(Direction.north()));
        assertEquals(southN, targetCell.getNeighbor(Direction.south()));
        assertNull(targetCell.getNeighbor(Direction.west()));
        assertNull(targetCell.getNeighbor(Direction.east()));

        assertEquals(northN_northN, northN.getNeighbor(Direction.north()));
        assertEquals(northN_southN, northN.getNeighbor(Direction.south()));
        assertNull(northN.getNeighbor(Direction.west()));
        assertNull(northN.getNeighbor(Direction.east()));

        assertEquals(northN, northN_northN.getNeighbor(Direction.south()));
        assertNull(northN_northN.getNeighbor(Direction.north()));
        assertNull(northN_northN.getNeighbor(Direction.west()));
        assertNull(northN_northN.getNeighbor(Direction.east()));
    }

    @Test
    void setNeighbor_mutualActions() {
        Cell targetCell = new Cell();
        Cell northN = new Cell();
        Cell new_northN = new Cell();

        targetCell.setNeighbor(Direction.north(), northN);
        assertEquals(northN, targetCell.getNeighbor(Direction.north()));
        assertEquals(targetCell, northN.getNeighbor(Direction.south()));

        targetCell.setNeighbor(Direction.north(), null);
        assertNull(targetCell.getNeighbor(Direction.north()));
        assertNull(northN.getNeighbor(Direction.south()));

        targetCell.setNeighbor(Direction.north(), new_northN);
        assertEquals(new_northN, targetCell.getNeighbor(Direction.north()));
        assertEquals(targetCell, new_northN.getNeighbor(Direction.south()));

        new_northN.setNeighbor(Direction.south(), null);
        assertNull(targetCell.getNeighbor(Direction.north()));
        assertNull(new_northN.getNeighbor(Direction.south()));
    }

    @Test
    void getDirectionsOfFreeNeighbors_sampleTest() {
        Cell targetCell = new Cell();
        Cell northN = new Cell();
        Cell southN = new Cell();
        southN.setSegment(new Tap());

        targetCell.setNeighbor(Direction.north(), northN);
        targetCell.setNeighbor(Direction.south(), southN);

        HashSet<Direction> expDirections = new HashSet<>() {{
            add(Direction.north());
        }};

        HashSet<Direction> resDirections = targetCell.getDirectionsOfFreeNeighbors();
        assertEquals(expDirections, resDirections);
    }

    @Test
    void getDirectionsOfFreeNeighbors_noFreeNeighbors() {
        Cell targetCell = new Cell();
        Cell northN = new Cell();
        Cell southN = new Cell();
        northN.setSegment(new Tap());
        southN.setSegment(new Tap());

        targetCell.setNeighbor(Direction.north(), northN);
        targetCell.setNeighbor(Direction.south(), southN);

        HashSet<Direction> expDirections = new HashSet<>();

        HashSet<Direction> resDirections = targetCell.getDirectionsOfFreeNeighbors();
        assertEquals(expDirections, resDirections);
    }
}