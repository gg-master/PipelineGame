package PipelineGame.model.pipeline.segments;

import PipelineGame.model.Cell;
import PipelineGame.model.events.IPipelineSegmentListener;
import PipelineGame.model.pipeline.Water;
import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.utils.Direction;

import java.util.*;

public abstract class Segment implements WaterFlowContext {

    protected HashSet<Direction> drainages;

    protected Water water;

    protected Cell cell;

    public void setCell(Cell cell) {
        if (this.cell == cell) {
            return;
        }
        Cell oldCell = this.cell;
        this.cell = cell;

        if (oldCell != null && oldCell.getSegment() == this) {
            oldCell.setSegment(null);
        }
        if (this.cell == null){
            return;
        }
        this.cell.setSegment(this);
    }

    public Cell getCell() {
        return this.cell;
    }

    public Segment getNeighborSegment(Direction direction){
        if (this.cell == null) {
            return null;
        }
        Cell neighbour = this.cell.getNeighbor(direction);

        if (neighbour == null) {
            return null;
        }
        return neighbour.getSegment();
    }

    public HashSet<Direction> getAvailableDirections() {
        return new HashSet<>(this.drainages);
    }
    public WaterFlowContext getNextContext(Direction direction) {
        return this.getNeighborSegment(direction);
    }

    public Water conductWater(Water water) {
        if (this.water != null) {
            this.water = this.water.mix(water);
        } else {
            this.water = water;
        }

        this.fireConductWater();
        return this.water;
    }

    public void rotateRight() {
        HashSet<Direction> newDrainages = new HashSet<>();

        for (Direction direction : this.drainages) {
           newDrainages.add(direction.clockwise());
        }
        this.drainages = newDrainages;
        this.fireSegmentRotate();
    }

    // ---------------------------------- Управление событиями сегмента -----------------------------------------------
    private final List<IPipelineSegmentListener> pipelineSegmentListeners = new ArrayList<>();

    public void addPipelineSegmentListener(IPipelineSegmentListener l) {
        pipelineSegmentListeners.add(l);
    }

    public void removePipelineSegmentListener(IPipelineSegmentListener l) {
        pipelineSegmentListeners.remove(l);
    }

    protected void fireConductWater() {
        for (IPipelineSegmentListener listener : pipelineSegmentListeners) {
            listener.onConductWater();
        }
    }

    protected void fireSegmentRotate() {
        for (IPipelineSegmentListener listener : pipelineSegmentListeners) {
            listener.onSegmentRotate();
        }
    }
}
