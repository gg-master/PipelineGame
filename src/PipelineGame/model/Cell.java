package PipelineGame.model;

import PipelineGame.model.utils.Direction;
import PipelineGame.model.pipeline.segments.Segment;

import java.util.HashMap;
import java.util.HashSet;

public class Cell {
    private final HashMap<Direction, Cell> neighbors = new HashMap<>() {{
        put(Direction.west(), null);
        put(Direction.east(), null);
        put(Direction.north(), null);
        put(Direction.south(), null);
    }};

    private Segment segment;

    public Cell getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public void setNeighbor(Direction direction, Cell neighbour) {
        Cell currentNeighbour = this.neighbors.get(direction);
        if (currentNeighbour == neighbour) {
            return;
        }
        this.neighbors.put(direction, neighbour);

        if (currentNeighbour != null && currentNeighbour.getNeighbor(direction.opposite()) == this) {
            currentNeighbour.setNeighbor(direction.opposite(), null);
        }
        if (neighbour == null) {
            return;
        }
        neighbour.setNeighbor(direction.opposite(), this);
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        if (this.segment == segment) {
            return;
        }
        Segment oldSegment = this.segment;
        this.segment = segment;

        if (oldSegment != null && oldSegment.getCell() == this) {
            oldSegment.setCell(null);
        }
        if (this.segment == null) {
            return;
        }
        this.segment.setCell(this);
    }

    public HashSet<Direction> getDirectionsOfFreeNeighbors() {
        HashSet<Direction> freeDirections = new HashSet<>();

        for (Direction direction : this.neighbors.keySet()) {
            Cell neighbor = this.getNeighbor(direction);
            if (neighbor != null && neighbor.getSegment() == null) {
                freeDirections.add(direction);
            }
        }
        return freeDirections;
    }
}