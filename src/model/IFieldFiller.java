package model;

import model.pipeline.segments.Hatch;
import model.pipeline.segments.Tap;

public interface IFieldFiller {
    void fillField(GameField field);

    Tap getTap();

    Hatch getHatch();
}
