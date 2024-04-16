package model.pipeline;

import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Hatch extends Segment {
    private Water expectedWater; // TODO мб что-то лучше придумать

    public Hatch() {
        this.drainages = new ArrayList<>(List.of(Direction.east()));
    }

    public void setExpectedWater(Water expectedWater) {
        this.expectedWater = expectedWater;
    }

    @Override
    public boolean conductWater() {
        return this.water.equals(expectedWater);
    }
}
