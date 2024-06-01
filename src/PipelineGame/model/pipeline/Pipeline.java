package PipelineGame.model.pipeline;

import PipelineGame.model.events.IPipelineListener;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.utils.Direction;

import java.util.*;

import static PipelineGame.AppSettings.waterFlowAnimationDurationInMillis;


public class Pipeline {
    private HashMap<Integer, HashSet<WaterFlowContext>> _pipelineBuildHistory;
    private HashMap<WaterFlowContext, HashSet<Direction>> _pourWaterContexts;
    private final Tap tap;
    private Hatch hatch;
    private int ticCounter = 0;

    public Pipeline(Tap tap) {
        this.tap = tap;
    }

    public void buildPipeline(Water initWater) {
        this.ticCounter = 0;
        this._pipelineBuildHistory = new HashMap<>();
        this._pourWaterContexts = new HashMap<>();

        Queue<WaterFlow> activeWaterFlows = new ArrayDeque<>();
        activeWaterFlows.add(new WaterFlow(initWater, this.tap));

        while (activeWaterFlows.size() != 0) {
            this.ticCounter++;
            List<WaterFlow> newFlows = runAllWaterFlows(activeWaterFlows);
            activeWaterFlows.addAll(newFlows);

            try {
                Thread.sleep(waterFlowAnimationDurationInMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<WaterFlow> runAllWaterFlows(Queue<WaterFlow> flows) {
        HashMap<WaterFlowContext, ArrayList<WaterFlow>> visitedSegments = new HashMap<>();

        while (flows.peek() != null) {
            runWaterFlow(flows.poll(), visitedSegments);
        }
        this._pipelineBuildHistory.put(this.ticCounter, new HashSet<>(visitedSegments.keySet()));

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
        for (HashSet<WaterFlowContext> ctxHashSet : this._pipelineBuildHistory.values()) {
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
        return new HashMap<>(this._pourWaterContexts);
    }

    public HashMap<Integer, HashSet<WaterFlowContext>> getPipelineBuildHistory() {
        return new HashMap<>(this._pipelineBuildHistory);
    }

    // ---------------------------------- Управление событиями пайплайна -----------------------------------------------
    private final List<IPipelineListener> pipelineListeners = new ArrayList<>();

    public void addPipeLineListener(IPipelineListener l) {
        pipelineListeners.add(l);
    }

    public void removePipelineListener(IPipelineListener l) {
        pipelineListeners.remove(l);
    }

    protected void firePourWater(WaterFlowContext context, Direction direction) {
        if (!this._pourWaterContexts.containsKey(context)) {
            this._pourWaterContexts.put(context, new HashSet<>());
        }
        this._pourWaterContexts.get(context).add(direction);

        for (IPipelineListener listener : pipelineListeners) {
            listener.onPourWater(context, direction);
        }
    }
}
