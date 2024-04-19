package model.fillers;

import model.Cell;
import model.GameField;
import model.IFieldFiller;
import model.pipeline.segments.Hatch;
import model.pipeline.segments.Tap;
import model.utils.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;


public class RandomizedFieldFiller implements IFieldFiller {
    private GameField field;
    private Tap tap;
    private Hatch hatch;

    private final SegmentFactory factory = new SegmentFactory();

    public void fillField(GameField field) {
        this.field = field;

        makeTap();
        makeHatch();

        createWinningRoute();
        fillVoidsInField();
    }

    public Tap getTap() {
        return this.tap;
    }

    public Hatch getHatch() {
        return this.hatch;
    }

    private void makeTap() {
        this.tap = this.factory.createTap();
        Random random = new Random();

        Point tapPos = new Point(
                random.nextInt(field.getDimension().width),
                random.nextInt(field.getDimension().height)
        );
        this.field.getCell(tapPos).setSegment(this.tap);
    }

    private void makeHatch() {
        this.hatch = this.factory.createHatch();
    }

    private void createWinningRoute() {
        int routeLength = (field.getDimension().height * field.getDimension().width) / 2;

        Cell prevCell = this.tap.getCell();
        Direction prevInputDir = null;
        PipeType prevSegmentType = null;

        for (int i = 0; i < routeLength; i++) {
            HashSet<Direction> variants = prevCell.getDirectionsOfFreeNeighbors();
            Map<Direction, PipeType> nextSegment = getNextPipeForWinningRoute(variants, prevInputDir, prevSegmentType);

            // Если у текущей клетки нет других вариантов для дальнейшей становки, то ставим в текущую конец.
            if (nextSegment == null) {
                prevCell.setSegment(this.hatch);
                return;
            }

            Direction chosenDir = nextSegment.keySet().iterator().next();

            prevSegmentType = nextSegment.get(chosenDir);
            prevCell = prevCell.getNeighbor(chosenDir);
            prevInputDir = chosenDir;
            prevCell.setSegment(this.factory.createPipe(prevSegmentType));
        }
        prevCell.setSegment(this.hatch);
    }
    private Map<Direction, PipeType> getNextPipeForWinningRoute(
            HashSet<Direction> variants, Direction prevInputDir, PipeType prevSegmentType
    ) {
        Random random = new Random();
        Map<Direction, PipeType> result  = new HashMap<>();
        List<Direction> keysOfVariants = new ArrayList<>(variants);

        // Если предыдущего направление нет, то добавляем Corner
        if (prevSegmentType == null) {
            result.put(keysOfVariants.get(random.nextInt(keysOfVariants.size())), PipeType.Corner);
            return result;
        }
        // Для построение трубопровода испольузем только сегменты с двумя входам
        List<PipeType> usingTypes = new ArrayList<>(Arrays.asList(PipeType.Corner, PipeType.Adapter));
        PipeType nextSegmentType = usingTypes.get(random.nextInt(usingTypes.size()));

        if (prevSegmentType == PipeType.Adapter) {
            if (!variants.contains(prevInputDir)) {
                return null;
            }
            result.put(prevInputDir, nextSegmentType);
        }
        else {
            Direction nextDir;

            if (prevInputDir.equals(Direction.south()) || prevInputDir.equals(Direction.north())) {
                nextDir = variants.contains(Direction.east()) ? Direction.east() :
                        variants.contains(Direction.west()) ? Direction.west() :
                                null;
            } else {
                nextDir = variants.contains(Direction.south()) ? Direction.south() :
                        variants.contains(Direction.north()) ? Direction.north() :
                                null;
            }
            if (nextDir == null) {
                return null;
            }
            result.put(nextDir, nextSegmentType);
        }
        return result;
    }

    private void fillVoidsInField() {
        Random random = new Random();
        int countOfTypes = PipeType.class.getEnumConstants().length;

        for (int i = 0; i < field.getDimension().height; i++) {
            for (int j = 0; j < field.getDimension().width; j++) {

                PipeType nextSegmentType = PipeType.class.getEnumConstants()[random.nextInt(countOfTypes)];
                Cell cell = field.getCell(i, j);
                if (cell.getSegment() == null) {
                    cell.setSegment(this.factory.createPipe(nextSegmentType));
                }
            }
        }
    }
}
