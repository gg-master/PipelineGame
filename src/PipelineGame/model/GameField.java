package PipelineGame.model;

import PipelineGame.model.utils.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameField {
    private final Dimension dimension;
    protected List<Cell> cells;

    public GameField(Dimension dimension) {
        if (dimension.width < 2 || dimension.height < 2) {
            throw new IndexOutOfBoundsException("Size of field must be more than 2x2");
        }

        this.dimension = dimension;
        this.recreateGameField();
    }

    public void recreateGameField() {
        this.cells = new ArrayList<>(this.dimension.width * this.dimension.height);

        for (int row = 0; row < this.dimension.height; row++) {
            for (int col = 0; col < this.dimension.width; col++) {
                Cell cell = new Cell();
                cell.setNeighbor(Direction.west(), getCell(row, col - 1));
                cell.setNeighbor(Direction.north(), getCell(row - 1, col));
                cells.add(cell);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if ((row < 0 || row >= this.dimension.height) ||
                (col < 0 || col >= this.dimension.width)) {
            return null;
        }
        return this.cells.get(this.dimension.width * row + col);
    }

    public Cell getCell(Point point) {
        return this.getCell(point.y, point.x);
    }

    public Dimension getDimension() {
        return new Dimension(this.dimension);
    }
}
