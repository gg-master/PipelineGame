package model.pipeline.segments.pipes;

import model.pipeline.segments.Pipe;
import model.utils.Direction;

import java.util.HashSet;
import java.util.List;

public class Corner extends Pipe {
    public Corner() {
        this.drainages = new HashSet<>(List.of(Direction.west(), Direction.north()));
    }
}
