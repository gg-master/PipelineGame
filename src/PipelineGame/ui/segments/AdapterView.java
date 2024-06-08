package PipelineGame.ui.segments;

import PipelineGame.model.pipeline.segments.pipes.Adapter;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.utils.ImageHelper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class AdapterView extends SegmentView{
    private static final ImageIcon icon = loadAsImageIcon("adapter.png");

    private static final HashMap<Set<Direction>, ImageIcon> icons = new HashMap<>(){{
        put(Set.of(Direction.west(), Direction.east()), icon);
        put(Set.of(Direction.north(), Direction.south()), ImageHelper.rotateIcon(icon));
    }};

    public AdapterView(Adapter adapter) {
        super(adapter);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections());
    }
}

