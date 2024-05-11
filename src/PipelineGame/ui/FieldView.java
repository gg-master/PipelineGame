package PipelineGame.ui;

import PipelineGame.model.GameField;
import PipelineGame.model.events.IPipelineListener;
import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.segments.pipes.Adapter;
import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.pipeline.segments.pipes.Cross;
import PipelineGame.model.pipeline.segments.pipes.Tee;
import PipelineGame.model.utils.Direction;
import PipelineGame.ui.segments.*;

import javax.swing.*;
import java.awt.*;


public class FieldView extends JPanel {
    public final PipelineListener pipelineListener = new PipelineListener();
    private final Dimension dimension;
    private final GameField gameField;

    public FieldView(GameField gameField) {
        this.gameField = gameField;
        this.dimension = gameField.getDimension();

        setDoubleBuffered(true);
        setLayout(new GridLayout(this.dimension.height, this.dimension.width));
        setBackground(Color.DARK_GRAY);
        this.createField();
    }

    private void createField() {
        int CELL_SIZE = SegmentView.SIDE_SIZE;
        Dimension fieldDimension = new Dimension(CELL_SIZE * this.dimension.width,
                CELL_SIZE * this.dimension.height);

        setPreferredSize(fieldDimension);
        setMinimumSize(fieldDimension);
        setMaximumSize(fieldDimension);

        this.repaintField();
    }

    private void repaintField() {
        removeAll();

        SegmentView segmentView;
        for (int row = 0; row < this.dimension.height; row++)
        {
            for (int col = 0; col < this.dimension.width; col++)
            {
                try {
                    Segment segment = this.gameField.getCell(row, col).getSegment();

                    if (segment == null){
                        continue;
                    }

                    if (segment instanceof Tap) {
                        segmentView = new TapView((Tap) segment);
                    } else if (segment instanceof Hatch) {
                        segmentView = new HatchView((Hatch) segment);
                    } else if (segment instanceof Adapter) {
                        segmentView = new AdapterView((Adapter) segment);
                    } else if (segment instanceof Corner) {
                        segmentView = new CornerView((Corner) segment);
                    } else if (segment instanceof Tee) {
                        segmentView = new TeeView((Tee) segment);
                    } else {
                        segmentView = new CrossView((Cross) segment);
                    }

                    add(segmentView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        validate();
    }

    public void setEnabledField(boolean isEnabled){
        for(Component c : this.getComponents()) {
            c.setEnabled(isEnabled);
        }
    }

    // ---------------------------------- Слушатель разлива воды -------------------------------------------------------
    private class PipelineListener implements IPipelineListener{

        @Override
        public void onPourWater(WaterFlowContext context, Direction direction) {
            for (Component c : getComponents()) {
                SegmentView segmentView = (SegmentView) c;
                if (segmentView.segment.equals(context)) {
                    segmentView.pourWater();
                    return;
                }
            }
        }
    }

}
