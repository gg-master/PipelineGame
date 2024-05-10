package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.ImageHelper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

public class CornerView extends SegmentView{
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "corner.png");

    private static final HashMap<Set<Direction>, ImageIcon> icons = new HashMap<>(){{
        put(Set.of(Direction.west(), Direction.north()), icon);
        put(Set.of(Direction.north(), Direction.east()), ImageHelper.rotateIcon(icon));
        put(Set.of(Direction.east(), Direction.south()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon)));
        put(Set.of(Direction.south(), Direction.west()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon))));
    }};

    public CornerView(Segment segment) {
        super(segment);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections());
    }
}
