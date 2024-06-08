package PipelineGame.ui.segments;

import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.utils.ImageHelper;

import javax.swing.*;
import java.util.HashMap;

import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class TapView extends SegmentView {
    private static final ImageIcon icon = loadAsImageIcon("tap.png");

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
