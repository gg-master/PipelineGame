package PipelineGame.model.events;

import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.utils.Direction;

import java.util.EventListener;

public interface IPipelineListener extends EventListener {
    void onPourWater(WaterFlowContext context, Direction direction);
}
