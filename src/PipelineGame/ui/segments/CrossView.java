package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.pipes.Cross;

import javax.swing.*;

public class CrossView extends SegmentView{
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "cross.png");

    public CrossView(Cross cross) {
        super(cross);
    }

    @Override
    protected ImageIcon createImage(){
        return icon;
    }
}

