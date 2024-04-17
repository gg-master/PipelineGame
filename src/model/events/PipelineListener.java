package model.events;

import model.pipeline.WaterFlowContext;
import model.utils.Direction;

import java.util.EventListener;

public interface PipelineListener extends EventListener {
    void onPourWater(WaterFlowContext context, Direction direction);
}
