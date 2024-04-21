import model.Cell;
import model.GameField;
import model.utils.Direction;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void recreateGameField_sampleTest() {
        GameField field = new GameField(new Dimension(2, 2));

        Cell leftTopCell = field.getCell(0, 0);
        assertNull(leftTopCell.getNeighbor(Direction.west()));
        assertNull(leftTopCell.getNeighbor(Direction.north()));
        assertNotNull(leftTopCell.getNeighbor(Direction.east()));
        assertNotNull(leftTopCell.getNeighbor(Direction.south()));

        field.recreateGameField();

        Cell newLeftTopCell = field.getCell(0, 0);

        assertNotEquals(leftTopCell, newLeftTopCell);

        assertNull(newLeftTopCell.getNeighbor(Direction.west()));
        assertNull(newLeftTopCell.getNeighbor(Direction.north()));
        assertNotNull(newLeftTopCell.getNeighbor(Direction.east()));
        assertNotNull(newLeftTopCell.getNeighbor(Direction.south()));
    }

    @Test
    void recreateGameField_tooSmallField() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> new GameField(new Dimension(1, 2))
        );
    }

    @Test
    void getCell_leftTopCell() {
        GameField field = new GameField(new Dimension(2, 2));

        Cell leftTopCell = field.getCell(0, 0);
        assertNull(leftTopCell.getNeighbor(Direction.west()));
        assertNull(leftTopCell.getNeighbor(Direction.north()));
        assertNotNull(leftTopCell.getNeighbor(Direction.east()));
        assertNotNull(leftTopCell.getNeighbor(Direction.south()));
    }

    @Test
    void getCell_rightTopCell() {
        GameField field = new GameField(new Dimension(2, 2));

        Cell rightTopCell = field.getCell(0, 1);
        assertNull(rightTopCell.getNeighbor(Direction.east()));
        assertNull(rightTopCell.getNeighbor(Direction.north()));
        assertNotNull(rightTopCell.getNeighbor(Direction.west()));
        assertNotNull(rightTopCell.getNeighbor(Direction.south()));
    }

    @Test
    void getCell_leftBotCell() {
        GameField field = new GameField(new Dimension(2, 2));

        Cell leftBotCell = field.getCell(1, 0);
        assertNull(leftBotCell.getNeighbor(Direction.west()));
        assertNull(leftBotCell.getNeighbor(Direction.south()));
        assertNotNull(leftBotCell.getNeighbor(Direction.north()));
        assertNotNull(leftBotCell.getNeighbor(Direction.east()));
    }

    @Test
    void getCell_rightBotCell() {
        GameField field = new GameField(new Dimension(2, 2));

        Cell rightBotCell = field.getCell(1, 1);
        assertNull(rightBotCell.getNeighbor(Direction.east()));
        assertNull(rightBotCell.getNeighbor(Direction.south()));
        assertNotNull(rightBotCell.getNeighbor(Direction.north()));
        assertNotNull(rightBotCell.getNeighbor(Direction.west()));
    }

    @Test
    void getCell_invalidCoordsOfCell() {
        GameField field = new GameField(new Dimension(2, 2));

        assertNull(field.getCell(10, 10));
        assertNull(field.getCell(-1, -1));
    }
}