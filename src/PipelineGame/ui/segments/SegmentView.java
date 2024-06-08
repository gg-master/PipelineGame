package PipelineGame.ui.segments;

import PipelineGame.model.events.IPipelineSegmentListener;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.ui.utils.ImageHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static PipelineGame.ui.utils.ImageHelper.getScaledIcon;

public abstract class SegmentView extends JButton {
    public static final int SIDE_SIZE = 80;
    public Segment segment;

    public SegmentView(Segment segment) {
        this.segment = segment;
        setIcon(getScaledIcon(createImage(), new Dimension(SIDE_SIZE, SIDE_SIZE)));

        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.DARK_GRAY);
        addActionListener(e -> rotateRight());

        segment.addPipelineSegmentListener(new PipelineSegmentListener());
    }

    public void setEnabledSegment(boolean isEnabled) {
        if (isEnabled) {
            addActionListener(e -> rotateRight());
        }
        else {
            for( ActionListener al : this.getActionListeners() ) {
                this.removeActionListener( al );
            }
        }
    }

    public void rotateRight() {
        this.segment.rotateRight();
        setIcon(ImageHelper.rotateIcon((ImageIcon)getIcon()));
    }

    public void pourWater(){
        setBackground(Color.RED);
    }

    protected void conductWater(){
        setBackground(Color.CYAN);
    }

    protected abstract ImageIcon createImage();

    // ---------------------------------- Слушатель модели сегмента ---------------------------------------------------

    private class PipelineSegmentListener implements IPipelineSegmentListener {

        @Override
        public void onConductWater() {
            conductWater();
        }

        @Override
        public void onSegmentRotate() {
        }
    }
}
