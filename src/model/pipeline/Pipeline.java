package model.pipeline;

import model.events.PipelineListener;
import model.pipeline.segments.Hatch;
import model.pipeline.segments.Tap;
import model.utils.Direction;

import java.util.*;

public class Pipeline {
    private HashMap<Integer, HashSet<WaterFlowContext>> pipelineBuildHistory;
    private HashMap<WaterFlowContext, HashSet<Direction>> pourWaterContexts;
    private final Tap tap;
    private Hatch hatch;
    private int ticCounter = 0;

    public Pipeline(Tap tap) {
        this.tap = tap;
    }
    public void buildPipeline(Water initWater) {
        ticCounter = 0;
        pipelineBuildHistory = new HashMap<>();
        pourWaterContexts = new HashMap<>();

        Queue<WaterFlow> activeWaterFlows = new ArrayDeque<>();
        activeWaterFlows.add(new WaterFlow(initWater, this.tap));

        while (activeWaterFlows.size() != 0) {
            ticCounter++;
            List<WaterFlow> newFlows = runAllWaterFlows(activeWaterFlows);
            activeWaterFlows.addAll(newFlows);
        }
    }
    private List<WaterFlow> runAllWaterFlows(Queue<WaterFlow> flows) {
        HashMap<WaterFlowContext, ArrayList<WaterFlow>> visitedSegments = new HashMap<>();

        while (flows.peek() != null) {
            runWaterFlow(flows.poll(), visitedSegments);
        }
        this.pipelineBuildHistory.put(this.ticCounter, new HashSet<>(visitedSegments.keySet()));

        List<WaterFlow> newFlows = new ArrayList<>();
        for (ArrayList<WaterFlow> nextFlows : visitedSegments.values()) {
            newFlows.addAll(nextFlows);
        }
        return newFlows;
    }

    private void runWaterFlow(WaterFlow waterFlow, HashMap<WaterFlowContext, ArrayList<WaterFlow>> visitedSegments) {
        WaterFlowContext context = waterFlow.context();

        Water updatedWater = context.conductWater(waterFlow.water());
        HashSet<Direction> waterDirections = updatedWater.getFlowDirections(context.getAvailableDirections());

        visitedSegments.put(context, new ArrayList<>());
        for (Direction direction : waterDirections) {
            WaterFlowContext nextContext = context.getNextContext(direction);

            if (nextContext == null || !nextContext.getAvailableDirections().contains(direction.opposite())) {
                firePourWater(context, direction);
                continue;
            }

            visitedSegments.get(context).add(
                    new WaterFlow(updatedWater.runWaterOnDirection(direction), nextContext)
            );
        }
    }

    public boolean isHatchReached() {
        if (this.hatch != null) {
            return true;
        }
        for (HashSet<WaterFlowContext> ctxHashSet : this.pipelineBuildHistory.values()) {
            for (WaterFlowContext context : ctxHashSet) {
                if (context instanceof Hatch) {
                    this.hatch = (Hatch) context;
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<WaterFlowContext, HashSet<Direction>> getPourWaterContexts() {
        return new HashMap<>(this.pourWaterContexts);
    }

    public HashMap<Integer, HashSet<WaterFlowContext>> getPipelineBuildHistory() {
        return new HashMap<>(this.pipelineBuildHistory);
    }

    // ---------------------------------- Управление событиями пайплайна -----------------------------------------------
    private final List<PipelineListener> pipelineListeners = new ArrayList<>();

    public void addGameStateListener(PipelineListener l) {
        pipelineListeners.add(l);
    }

    public void removeGameStateListener(PipelineListener l) {
        pipelineListeners.remove(l);
    }

    protected void firePourWater(WaterFlowContext context, Direction direction) {
        if (!this.pourWaterContexts.containsKey(context)) {
            this.pourWaterContexts.put(context, new HashSet<>());
        }
        this.pourWaterContexts.get(context).add(direction);

        for (PipelineListener listener : pipelineListeners) {
            listener.onPourWater(context, direction);
        }
    }
}
