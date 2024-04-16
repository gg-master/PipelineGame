package model.events;

import java.util.EventListener;

public interface PipelineSegmentListener extends EventListener {
    void conductWater();
    void pourWater();
    void onSegmentRotate();
}
