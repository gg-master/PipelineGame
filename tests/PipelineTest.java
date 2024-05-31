import PipelineGame.model.GameField;
import PipelineGame.model.pipeline.Pipeline;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.pipeline.segments.pipes.Tee;
import PipelineGame.model.utils.Direction;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PipelineTest {

    @Test
    void buildPipeline_sampleTest() {
        GameField field = new GameField(new Dimension(2, 2));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(0, 1).setSegment(new Corner());
        field.getCell(1, 0).setSegment(new Corner());
        field.getCell(1, 1).setSegment(new Hatch());

        Segment segment = field.getCell(0, 1).getSegment();
        segment.rotateRight();
        segment.rotateRight();
        segment.rotateRight();

        segment = field.getCell(1, 1).getSegment();
        segment.rotateRight();
        segment.rotateRight();
        segment.rotateRight();

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());

        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
            put(2, new HashSet<>() {{ add(field.getCell(0, 1).getSegment()); }});
            put(3, new HashSet<>() {{ add(field.getCell(1, 1).getSegment()); }});
        }};

        assertEquals(expHistory, pipeline.getPipelineBuildHistory());
        assertEquals(0, pipeline.getPourWaterContexts().size());
        assertTrue(pipeline.isHatchReached());
    }

    @Test
    void buildPipeline_onePourWater() {
        GameField field = new GameField(new Dimension(2, 2));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(1, 1).setSegment(new Hatch());

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());

        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
        }};

        HashMap<WaterFlowContext, HashSet<Direction>> expPourWaterContext = new HashMap<>() {{
            put(field.getCell(0, 0).getSegment(), new HashSet<>() {{ add(Direction.east()); }});
        }};
        assertEquals(expHistory, pipeline.getPipelineBuildHistory());
        assertEquals(1, pipeline.getPourWaterContexts().size());
        assertEquals(expPourWaterContext, pipeline.getPourWaterContexts());
        assertFalse(pipeline.isHatchReached());
    }

    @Test
    void buildPipeline_multiplePourWater() {
        GameField field = new GameField(new Dimension(3, 3));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(0,1).setSegment(new Tee());

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());

        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
            put(2, new HashSet<>() {{ add(field.getCell(0, 1).getSegment()); }});

        }};

        HashMap<WaterFlowContext, HashSet<Direction>> expPourWaterContext = new HashMap<>() {{
            put(field.getCell(0, 1).getSegment(), new HashSet<>() {{
                add(Direction.east());
                add(Direction.north());
            }});
        }};
        assertEquals(expHistory, pipeline.getPipelineBuildHistory());
        assertEquals(1, pipeline.getPourWaterContexts().size());
        assertEquals(expPourWaterContext, pipeline.getPourWaterContexts());
    }

    @Test
    void buildPipeline_multipleWaterFlowsNoCycle() {
        GameField field = new GameField(new Dimension(3, 3));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(0,1).setSegment(new Tee());
        field.getCell(0,2).setSegment(new Corner());
        field.getCell(1,1).setSegment(new Corner());
        field.getCell(1,2).setSegment(new Tee());
        field.getCell(2,2).setSegment(new Hatch());

        field.getCell(0, 1).getSegment().rotateRight();
        field.getCell(0, 1).getSegment().rotateRight();

        field.getCell(0, 2).getSegment().rotateRight();
        field.getCell(0, 2).getSegment().rotateRight();
        field.getCell(0, 2).getSegment().rotateRight();

        field.getCell(1, 1).getSegment().rotateRight();

        field.getCell(1, 2).getSegment().rotateRight();
        field.getCell(1, 2).getSegment().rotateRight();
        field.getCell(1, 2).getSegment().rotateRight();

        field.getCell(2, 2).getSegment().rotateRight();
        field.getCell(2, 2).getSegment().rotateRight();
        field.getCell(2, 2).getSegment().rotateRight();

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());
        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
            put(2, new HashSet<>() {{ add(field.getCell(0, 1).getSegment()); }});
            put(3, new HashSet<>() {{
                add(field.getCell(0, 2).getSegment());
                add(field.getCell(1, 1).getSegment());
            }});
            put(4, new HashSet<>() {{
                add(field.getCell(1, 2).getSegment());
            }});
            put(5, new HashSet<>() {{
                add(field.getCell(2, 2).getSegment());
            }});
        }};

        assertEquals(expHistory, pipeline.getPipelineBuildHistory());
        assertEquals(0, pipeline.getPourWaterContexts().size());
        assertTrue(pipeline.isHatchReached());
    }

    @Test
    void buildPipeline_multipleWaterFlowsWithCycle() {
        GameField field = new GameField(new Dimension(3, 3));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(0,1).setSegment(new Tee());
        field.getCell(0,2).setSegment(new Corner());
        field.getCell(1,1).setSegment(new Corner());
        field.getCell(1,2).setSegment(new Corner());

        field.getCell(0, 1).getSegment().rotateRight();
        field.getCell(0, 1).getSegment().rotateRight();

        field.getCell(0, 2).getSegment().rotateRight();
        field.getCell(0, 2).getSegment().rotateRight();
        field.getCell(0, 2).getSegment().rotateRight();

        field.getCell(1, 1).getSegment().rotateRight();

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());
        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
            put(2, new HashSet<>() {{ add(field.getCell(0, 1).getSegment()); }});
            put(3, new HashSet<>() {{
                add(field.getCell(0, 2).getSegment());
                add(field.getCell(1, 1).getSegment());
            }});
            put(4, new HashSet<>() {{
                add(field.getCell(1, 2).getSegment());
            }});
        }};

        assertEquals(expHistory, pipeline.getPipelineBuildHistory());
        assertEquals(0, pipeline.getPourWaterContexts().size());
        assertFalse(pipeline.isHatchReached());
    }

    @Test
    void getPourWaterContexts_testResultCopying() {
        GameField field = new GameField(new Dimension(2, 2));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(1, 1).setSegment(new Hatch());

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());

        HashMap<WaterFlowContext, HashSet<Direction>> expPourWaterContext = new HashMap<>() {{
            put(field.getCell(0, 0).getSegment(), new HashSet<>() {{ add(Direction.east()); }});
        }};

        HashMap<WaterFlowContext, HashSet<Direction>> pourWaterContexts = pipeline.getPourWaterContexts();
        assertEquals(1, pourWaterContexts.size());
        assertEquals(expPourWaterContext, pourWaterContexts);

        pourWaterContexts.put(new Hatch(), new HashSet<>());
        assertNotEquals(pourWaterContexts, pipeline.getPourWaterContexts());
    }

    @Test
    void getPipelineBuildHistory_testResultCopying() {
        GameField field = new GameField(new Dimension(2, 2));
        field.getCell(0,0).setSegment(new Tap());
        field.getCell(0, 1).setSegment(new Corner());
        field.getCell(1, 0).setSegment(new Corner());
        field.getCell(1, 1).setSegment(new Hatch());

        Segment segment = field.getCell(0, 1).getSegment();
        segment.rotateRight();
        segment.rotateRight();
        segment.rotateRight();

        segment = field.getCell(1, 1).getSegment();
        segment.rotateRight();
        segment.rotateRight();
        segment.rotateRight();

        Pipeline pipeline = new Pipeline((Tap) field.getCell(0, 0).getSegment());
        pipeline.buildPipeline(new Water());

        HashMap<Integer, HashSet<WaterFlowContext>> expHistory = new HashMap<>() {{
            put(1, new HashSet<>() {{ add(field.getCell(0, 0).getSegment()); }});
            put(2, new HashSet<>() {{ add(field.getCell(0, 1).getSegment()); }});
            put(3, new HashSet<>() {{ add(field.getCell(1, 1).getSegment()); }});
        }};

        HashMap<Integer, HashSet<WaterFlowContext>> resHistory = pipeline.getPipelineBuildHistory();
        assertEquals(expHistory, resHistory);

        resHistory.put(0, new HashSet<>());
        assertNotEquals(resHistory, pipeline.getPipelineBuildHistory());
    }
}