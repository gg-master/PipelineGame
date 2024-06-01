package PipelineGame.model.pipeline.segments.pipes;

import PipelineGame.model.pipeline.segments.Pipe;
import PipelineGame.model.utils.Direction;

import java.util.HashSet;
import java.util.List;

public class Cross extends Pipe {
    public Cross() {
        this._drainages = new HashSet<>(List.of(Direction.west(), Direction.east(), Direction.north(), Direction.south()));
    }

}
