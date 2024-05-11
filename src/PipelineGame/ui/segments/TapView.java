package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.ImageHelper;

import javax.swing.*;
import java.util.HashMap;

public class TapView extends SegmentView {
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "tap.png");

    private static final HashMap<Direction, ImageIcon> icons = new HashMap<>(){{
        put(Direction.east(), icon);
        put(Direction.south(), ImageHelper.rotateIcon(icon));
        put(Direction.west(), ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon)));
        put(Direction.north(), ImageHelper.rotateIcon(ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon))));
    }};

    public TapView(Tap tap) {
        super(tap);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections().iterator().next());
    }
}
