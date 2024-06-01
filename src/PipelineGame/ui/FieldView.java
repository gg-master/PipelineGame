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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static PipelineGame.AppSettings.*;


public class FieldView extends JPanel {
    public final PipelineListener pipelineListener = new PipelineListener();
    private final Dimension dimension;
    private final GameField gameField;

    private final Map<Class<?>, Function<Object, SegmentView>> segmentViewMap = new HashMap<>(){{
        put(Tap.class, segment -> new TapView((Tap) segment));
        put(Hatch.class, segment -> new HatchView((Hatch) segment));
        put(Adapter.class, segment -> new AdapterView((Adapter) segment));
        put(Corner.class, segment -> new CornerView((Corner) segment));
        put(Tee.class, segment -> new TeeView((Tee) segment));
    }};

    public FieldView(GameField gameField) {
        this.gameField = gameField;
        this.dimension = gameField.getDimension();

        setDoubleBuffered(true);
        setLayout(new GridLayout(this.dimension.height, this.dimension.width));
        setBackground(Color.DARK_GRAY);
        this.createField();
    }

    private void createField() {
        SegmentView.SIDE_SIZE = getOptimalSegmentSideSize();

        int CELL_SIZE = SegmentView.SIDE_SIZE;
        Dimension fieldDimension = new Dimension(CELL_SIZE * this.dimension.width,
                CELL_SIZE * this.dimension.height);

        setPreferredSize(fieldDimension);
        setMinimumSize(fieldDimension);
        setMaximumSize(fieldDimension);

        this.repaintField();
    }

    private int getOptimalSegmentSideSize() {
        if (this.dimension.width > 15 || this.dimension.height > 8) {
            return SMALL_SEGMENT_SIDE_SIZE;
        } else if (this.dimension.width <= 3 && this.dimension.height <= 3) {
            return BIG_SEGMENT_SIDE_SIZE;
        }
        return DEFAULT_SEGMENT_SIDE_SIZE;
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
                    segmentView = segmentViewMap.getOrDefault(
                            segment.getClass(),
                            segmentObj -> new CrossView((Cross) segmentObj)
                    ).apply(segment);

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
            ((SegmentView)c).setEnabledSegment(isEnabled);
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
