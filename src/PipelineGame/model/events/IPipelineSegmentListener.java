package PipelineGame.model.events;

import java.util.EventListener;

public interface IPipelineSegmentListener extends EventListener {
    void onConductWater();
    void onSegmentRotate();
}
