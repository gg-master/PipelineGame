package PipelineGame.model.pipeline;


import PipelineGame.model.pipeline.water.Water;

public record WaterFlow(Water water, WaterFlowContext context) {
}
