package model.pipeline;

import model.events.PipelineListener;
import model.events.WaterFlowCheckResultEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pipeline {
    private final List<Segment> segments = new ArrayList<Segment>();
    private Tap tap;
    private Hatch hatch;

    public boolean runWaterFlowCheck(Water initWater, Water expectedWater) {
        this.tap.setInitWater(initWater);
        this.hatch.setExpectedWater(expectedWater);

        boolean checkResult = this.tap.conductWater();
        this.fireOnWaterFlowCheck(checkResult);

        return checkResult;
    }

    public void addSegment(Segment segment) {
        segment.setPipeline(this); // TODO возможно создние взаимной связи стоит сделать не здесь

        if (segment instanceof Tap) {
            this.tap = (Tap) segment;
        } else if (segment instanceof Hatch) {
            this.hatch = (Hatch) segment;
        } else {
            this.segments.add(segment);
        }
    }

    public void randomRotateSegments() {
        for (Segment s : this.segments) {
            Random random = new Random();
            int shuffleCount = random.nextInt(3);

            for (int i = 0; i < shuffleCount; i++) {
                s.rotateRight();
            }
        }
    }

//    public Segment getLeftTop

    // ---------------------------------- Управление событиями сегмента -----------------------------------------------
    private final List<PipelineListener> pipelineListeners = new ArrayList<>();

    public void addPipelineSegmentListener(PipelineListener l) {
        pipelineListeners.add(l);
    }

    public void removePipelineSegmentListener(PipelineListener l) {
        pipelineListeners.remove(l);
    }

    protected void fireOnWaterFlowCheck(boolean result) {
        // TODO мб упростить все это? Подумать!
        WaterFlowCheckResultEvent event = new WaterFlowCheckResultEvent(this);
        event.setAssembled(result);

        for (PipelineListener listener : pipelineListeners) {
            listener.onWaterFlowCheck(event);
        }
    }
}
