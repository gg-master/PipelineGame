package PipelineGame.model.pipeline.segments.pipes;

import PipelineGame.model.pipeline.segments.Pipe;
import PipelineGame.model.utils.Direction;

import java.util.HashSet;
import java.util.List;

public class Adapter extends Pipe {
    public Adapter() {
        this._drainages = new HashSet<>(List.of(Direction.west(), Direction.east()));
    }
}
