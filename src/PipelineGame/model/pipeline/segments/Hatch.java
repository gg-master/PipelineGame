package PipelineGame.model.pipeline.segments;

import PipelineGame.model.utils.Direction;

import java.util.HashSet;
import java.util.List;

public class Hatch extends Segment {
    public Hatch() {
        this.drainages = new HashSet<>(List.of(Direction.east()));
    }
}
