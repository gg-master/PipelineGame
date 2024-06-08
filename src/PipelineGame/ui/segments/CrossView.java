package PipelineGame.ui.segments;

import PipelineGame.model.pipeline.segments.pipes.Cross;

import javax.swing.*;

import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class CrossView extends SegmentView{
    private static final ImageIcon icon = loadAsImageIcon("cross.png");

    public CrossView(Cross cross) {
        super(cross);
    }

    @Override
    protected ImageIcon createImage(){
        return icon;
    }
}

