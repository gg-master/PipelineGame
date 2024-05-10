package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.ImageHelper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

public class AdapterView extends SegmentView{
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "adapter.png");

    private static final HashMap<Set<Direction>, ImageIcon> icons = new HashMap<>(){{
        put(Set.of(Direction.west(), Direction.east()), icon);
        put(Set.of(Direction.north(), Direction.south()), ImageHelper.rotateIcon(icon));
    }};

    public AdapterView(Segment segment) {
        super(segment);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections());
    }
}

