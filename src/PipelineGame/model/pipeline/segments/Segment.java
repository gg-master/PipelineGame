package PipelineGame.model.pipeline.segments;

import PipelineGame.model.Cell;
import PipelineGame.model.events.IPipelineSegmentListener;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.utils.Direction;

import java.util.*;

public abstract class Segment implements WaterFlowContext {
    protected HashSet<Direction> _drainages;

    private Cell cell;
    private Water _water;
    private final List<WaterDevice> _devices = new ArrayList<>();

    public void addDevice(WaterDevice device) {
        this._devices.add(device);
    }

    public ArrayList<WaterDevice> getDevices() {
        return new ArrayList<>(this._devices);
    }

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
        return new HashSet<>(this._drainages);
    }

    public WaterFlowContext getNextContext(Direction direction) {
        return this.getNeighborSegment(direction);
    }

    public Water conductWater(Water water) {
        if (this._water != null) {
            this._water = this._water.mix(water);
        } else {
            this._water = water.clone();
        }
        for (WaterDevice device : this._devices) {
            this._water = device.conductWater(this._water);
        }
        this.fireConductWater();
        return this._water.clone();
    }

    public Water getWater() {
        return this._water.clone();
    }

    public void rotateRight() {
        HashSet<Direction> newDrainages = new HashSet<>();

        for (Direction direction : this._drainages) {
           newDrainages.add(direction.clockwise());
        }
        this._drainages = newDrainages;
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
