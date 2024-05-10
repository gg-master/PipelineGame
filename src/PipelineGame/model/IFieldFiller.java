package PipelineGame.model;

import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Tap;

public interface IFieldFiller {
    void fillField(GameField field);

    Tap getTap();

    Hatch getHatch();
}
