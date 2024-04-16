package model.pipeline;

import model.events.PipelineSegmentListener;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Segment {
    public Segment leftNeighbor;
    public Segment rightNeighbor;
    public Segment topNeighbor;
    public Segment bottomNeighbor;

    protected Water water;
    protected Pipeline pipeline;
    protected List<Direction> drainages;
    protected List<Direction> usedDrainages;

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public boolean conductWater() {
        Map<Direction, Segment> neighborsByDirections  = new HashMap<>() {{
            put(Direction.west(), leftNeighbor);
            put(Direction.east(), rightNeighbor);
            put(Direction.north(), topNeighbor);
            put(Direction.south(), bottomNeighbor);
        }};

        for (Direction outDir : drainages) {
            Segment neighbor = neighborsByDirections.get(outDir);
            if (neighbor != null && neighbor.haveDrainageOnIncomingDirection(outDir)) {
                neighbor.setWaterOnIncomingDirection(this.water, outDir);
            } else {
                this.firePourWater();
                return false;
            }
        }

        this.fireConductWater();

        for (Direction outDir : drainages) {
            Segment neighbor = neighborsByDirections.get(outDir);

            if (!neighbor.conductWater()) {
                return false;
            }
        }
        return true;
    }

    public void rotateRight() {
        for (Direction direction : this.drainages) {
            direction.clockwise();
        }
        this.fireSegmentRotate();
    }

    public boolean haveDrainageOnIncomingDirection(Direction direction) {
        return drainages.contains(direction.opposite());
    }

    public void setWaterOnIncomingDirection(Water water, Direction direction) {
        if (!this.haveDrainageOnIncomingDirection(direction) && !this.usedDrainages.contains(direction)){
            return;
        }
        this.water = water;
        this.usedDrainages.add(direction);
    }

    // ---------------------------------- Управление событиями сегмента -----------------------------------------------
    private final List<PipelineSegmentListener> pipelineSegmentListeners = new ArrayList<>();

    public void addPipelineSegmentListener(PipelineSegmentListener l) {
        pipelineSegmentListeners.add(l);
    }

    public void removePipelineSegmentListener(PipelineSegmentListener l) {
        pipelineSegmentListeners.remove(l);
    }

    protected void fireConductWater() {
        for (PipelineSegmentListener listener : pipelineSegmentListeners) {
            listener.conductWater();
        }
    }

    protected void firePourWater() {
        for (PipelineSegmentListener listener : pipelineSegmentListeners) {
            listener.pourWater();
        }
    }

    protected void fireSegmentRotate() {
        for (PipelineSegmentListener listener : pipelineSegmentListeners) {
            listener.onSegmentRotate();
        }
    }

}
