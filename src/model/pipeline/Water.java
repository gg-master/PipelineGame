package model.pipeline;

import model.utils.Direction;

import java.util.HashSet;

public class Water {
    protected HashSet<Direction> usedFlowDirections = new HashSet<>();

    public HashSet<Direction> getFlowDirections(HashSet<Direction> directions) {
        directions.removeAll(this.usedFlowDirections);
        return directions;
    }

    public Water runWaterOnDirection(Direction direction) {
        if (usedFlowDirections.contains(direction)) {
            return null;
        }
        Water water = new Water();
        water.usedFlowDirections.add(direction.opposite());
        return water;
    }
    public Water mix(Water otherWater) {
        Water newWater = new Water();
        newWater.usedFlowDirections.addAll(this.usedFlowDirections);
        newWater.usedFlowDirections.addAll(otherWater.usedFlowDirections);
        return newWater;
    }
    @Override
    public boolean equals(Object object) {
        return object instanceof Water;
    }
}
