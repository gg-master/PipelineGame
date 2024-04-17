package model.events;

import java.util.EventListener;

public interface PipelineSegmentListener extends EventListener {
    void onConductWater();
    void onSegmentRotate();
}
