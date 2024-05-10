import PipelineGame.model.Cell;
import PipelineGame.model.GameField;
import PipelineGame.model.fillers.RandomizedFieldFiller;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.Tap;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedFieldFillerTest {

    @Test
    void fillField_sampleTest() {
        Dimension d = new Dimension(10, 10);
        GameField field = new GameField(d);
        RandomizedFieldFiller filler = new RandomizedFieldFiller();

        filler.fillField(field);

        boolean haveTap = false;
        boolean haveHatch = false;
        for (int row = 0; row < d.height; row++) {
            for (int col = 0; col < d.width; col++) {
                Cell cell = field.getCell(row, col);
                assertNotNull(cell);
                if (cell.getSegment() instanceof Tap) {
                    haveTap = true;
                }
                if (cell.getSegment() instanceof Hatch) {
                    haveHatch = true;
                }
            }
        }
        assertTrue(haveTap);
        assertTrue(haveHatch);
    }

    @Test
    void getTap_returningSameTapTest() {
        Dimension d = new Dimension(10, 10);
        GameField field = new GameField(d);
        RandomizedFieldFiller filler = new RandomizedFieldFiller();

        filler.fillField(field);

        Tap tap = null;
        for (int row = 0; row < d.height; row++) {
            for (int col = 0; col < d.width; col++) {
                Segment segment = field.getCell(row, col).getSegment();
                if (segment instanceof Tap) {
                    assertNull(tap);
                    tap = (Tap) segment;
                }
            }
        }
        assertEquals(tap, filler.getTap());
    }

    @Test
    void getHatch_returningSameHatchTest() {
        Dimension d = new Dimension(10, 10);
        GameField field = new GameField(d);
        RandomizedFieldFiller filler = new RandomizedFieldFiller();

        filler.fillField(field);

        Hatch hatch = null;
        for (int row = 0; row < d.height; row++) {
            for (int col = 0; col < d.width; col++) {
                Segment segment = field.getCell(row, col).getSegment();
                if (segment instanceof Hatch) {
                    assertNull(hatch);
                    hatch = (Hatch) segment;
                }
            }
        }
        assertEquals(hatch, filler.getHatch());
    }
}