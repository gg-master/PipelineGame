package model.events;

import java.util.EventListener;

public interface PipelineListener extends EventListener {
    void onWaterFlowCheck(WaterFlowCheckResultEvent result);
}
