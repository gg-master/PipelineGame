package model.pipeline.pipes;

import model.pipeline.Pipe;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends Pipe {
    public Adapter() {
        this.drainages = new ArrayList<>(List.of(Direction.west(), Direction.east()));
    }
}
