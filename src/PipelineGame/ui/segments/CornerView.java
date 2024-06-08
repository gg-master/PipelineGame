package PipelineGame.ui.segments;

import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.utils.ImageHelper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class CornerView extends SegmentView{
    private static final ImageIcon icon = loadAsImageIcon("corner.png");

    private static final HashMap<Set<Direction>, ImageIcon> icons = new HashMap<>(){{
        put(Set.of(Direction.west(), Direction.north()), icon);
        put(Set.of(Direction.north(), Direction.east()), ImageHelper.rotateIcon(icon));
        put(Set.of(Direction.east(), Direction.south()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon)));
        put(Set.of(Direction.south(), Direction.west()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon))));
    }};

    public CornerView(Corner corner) {
        super(corner);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections());
    }
}
