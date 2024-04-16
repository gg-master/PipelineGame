package model.pipeline.pipes;

import model.pipeline.Pipe;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Corner extends Pipe {
    public Corner() {
        this.drainages = new ArrayList<>(List.of(Direction.west(), Direction.north()));
    }
}
