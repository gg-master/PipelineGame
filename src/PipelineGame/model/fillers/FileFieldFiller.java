package PipelineGame.model.fillers;

import PipelineGame.model.IFieldFiller;
import PipelineGame.model.GameField;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Tap;

public class FileFieldFiller implements IFieldFiller {
    private Tap tap;
    private Hatch hatch;
    public Tap getTap() {
        return this.tap;
    }

    public Hatch getHatch() {
        return this.hatch;
    }

    public void fillField(GameField field) {
        SegmentFactory factory = new SegmentFactory();

        this.tap = factory.createTap();
        field.getCell(0, 0).setSegment(this.tap);

        field.getCell(0, 1).setSegment(factory.createPipe(PipeType.Tee));
        field.getCell(0, 2).setSegment(factory.createPipe(PipeType.Corner));
        field.getCell(1, 0).setSegment(factory.createPipe(PipeType.Cross));
        field.getCell(1, 1).setSegment(factory.createPipe(PipeType.Corner));
        field.getCell(1, 2).setSegment(factory.createPipe(PipeType.Corner));
        field.getCell(2, 0).setSegment(factory.createPipe(PipeType.Corner));
        field.getCell(2, 1).setSegment(factory.createPipe(PipeType.Corner));

        this.hatch = factory.createHatch();
        field.getCell(2, 2).setSegment(factory.createPipe(PipeType.Corner));
    }
}
