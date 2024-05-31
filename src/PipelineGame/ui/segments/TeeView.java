package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.pipes.Tee;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.ImageHelper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

public class TeeView extends SegmentView{
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "tee.png");

    private static final HashMap<Set<Direction>, ImageIcon> icons = new HashMap<>(){{
        put(Set.of(Direction.west(), Direction.east(), Direction.north()), icon);
        put(Set.of(Direction.south(), Direction.east(), Direction.north()), ImageHelper.rotateIcon(icon));
        put(Set.of(Direction.south(), Direction.east(), Direction.west()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon)));
        put(Set.of(Direction.south(), Direction.north(), Direction.west()), ImageHelper.rotateIcon(ImageHelper.rotateIcon(ImageHelper.rotateIcon(icon))));
    }};

    public TeeView(Tee tee) {
        super(tee);
    }

    @Override
    protected ImageIcon createImage(){
        return icons.get(this.segment.getAvailableDirections());
    }
}

