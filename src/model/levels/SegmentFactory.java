package model.levels;

import model.pipeline.Hatch;
import model.pipeline.Pipe;
import model.pipeline.Tap;
import model.pipeline.pipes.Adapter;
import model.pipeline.pipes.Corner;

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
        }
        return new Adapter();
    }
}
