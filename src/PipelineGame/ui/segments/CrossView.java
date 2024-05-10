package PipelineGame.ui.segments;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.segments.Segment;

import javax.swing.*;

public class CrossView extends SegmentView{
    private static final ImageIcon icon = new ImageIcon(AppSettings.pathToImages + "cross.png");

    public CrossView(Segment segment) {
        super(segment);
    }

    @Override
    protected ImageIcon createImage(){
        return icon;
    }
}

