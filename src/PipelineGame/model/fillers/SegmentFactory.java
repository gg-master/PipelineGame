package PipelineGame.model.fillers;

import PipelineGame.model.pipeline.segments.Pipe;
import PipelineGame.model.pipeline.segments.pipes.Adapter;
import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.segments.pipes.Cross;
import PipelineGame.model.pipeline.segments.pipes.Tee;

public class SegmentFactory {
    public Tap createTap() {
        return new Tap();
    }

    public Hatch createHatch() {
        return new Hatch();
    }

    public Pipe createPipe(PipeType type) {
        if (type == PipeType.Corner) {
            return new Corner();
        } else if (type == PipeType.Tee) {
            return new Tee();
        } else if (type == PipeType.Cross) {
            return new Cross();
        } else if (type == PipeType.Adapter) {
            return new Adapter();
        }
        throw new RuntimeException("Unknown PipeType");
    }
}
