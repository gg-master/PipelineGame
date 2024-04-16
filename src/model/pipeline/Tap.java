package model.pipeline;

import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Tap extends Segment {
    public Tap() {
        this.drainages = new ArrayList<>(List.of(Direction.east()));
    }

    public void setInitWater(Water initWater) {
        this.usedDrainages = this.drainages;
        this.water = initWater;
    }
}
