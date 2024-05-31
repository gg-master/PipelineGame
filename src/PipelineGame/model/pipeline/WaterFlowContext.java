package PipelineGame.model.pipeline;

import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.utils.Direction;

import java.util.HashSet;

public interface WaterFlowContext {
    HashSet<Direction> getAvailableDirections();
    WaterFlowContext getNextContext(Direction direction);
    Water conductWater(Water water);
}
