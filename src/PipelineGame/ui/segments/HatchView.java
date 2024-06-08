package PipelineGame.ui.segments;

import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.utils.ImageHelper;

import javax.swing.*;
import java.util.HashMap;

import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class HatchView extends SegmentView {
    private static final ImageIcon icon = loadAsImageIcon("hatch.png");

    private static final HashMap<Direction, ImageIcon> icons = new HashMap<>(){{
        put(Direction.east(), icon);
        put(Direction.south(), ImageHelper.rotateIcon(icon));
        put(Direction.west(), ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon)));
        put(Direction.north(), ImageHelper.rotateIcon(ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon))));
    }};

    public HatchView(Hatch hatch) {
        super(hatch);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections().iterator().next());
    }
}
