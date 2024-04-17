package model.pipeline;

import model.utils.Direction;

import java.util.HashSet;

public interface WaterFlowContext {
    HashSet<Direction> getAvailableDirections();
    WaterFlowContext getNextContext(Direction direction);
    Water conductWater(Water water);
}
