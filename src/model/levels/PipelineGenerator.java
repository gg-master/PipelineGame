package model.levels;

import model.IMakePipeline;
import model.pipeline.Pipeline;
import model.pipeline.Segment;
import model.utils.Direction;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;


class Field {
    private final int width;
    private final int height;

    ArrayList<ArrayList<Segment>> field = new ArrayList<>();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        this.initializeField();
    }

    private void initializeField() {
        for (int i = 0; i < this.height; i++) {
            ArrayList<Segment> row = new ArrayList<>();
            for (int j = 0; j < this.width; j++) {
                row.add(null);
            }
            field.add(row);
        }
    }

    private boolean isFieldContainsPoint(Point pos) {
        int x = pos.x; int y = pos.y;
        return x >= 0 && x < field.get(0).size() && y >= 0 && y < field.size();
    }

    public void setSegment(Point pos, Segment segment) {
        if (!isFieldContainsPoint(pos)) {
            return;
        }
        field.get(pos.y).set(pos.x, segment);
    }

    public Segment getSegment(Point pos) {
        if (!isFieldContainsPoint(pos)) {
            return null;
        }
        return field.get(pos.y).get(pos.x);
    }

    public Map<Direction, Point> getFreeNeighborsFor(Point pos) {
        Map<Direction, Point> neighborsByDirections  = new HashMap<>() {{
            put(Direction.west(), new Point(pos.x - 1, pos.y));
            put(Direction.east(), new Point(pos.x + 1, pos.y));
            put(Direction.north(), new Point(pos.x, pos.y - 1));
            put(Direction.south(), new Point(pos.x, pos.y + 1));
        }};

        Map<Direction, Point> availablePositions  = new HashMap<>();

        for (Map.Entry<Direction, Point> entry : neighborsByDirections.entrySet()) {
            Direction direction = entry.getKey();
            Point point = entry.getValue();

            if (isFieldContainsPoint(point) && this.getSegment(point) == null) {
                availablePositions.put(direction, point);
            }
        }
        return availablePositions;
    }
}


public class PipelineGenerator implements IMakePipeline {
    private final Dimension FIELD_DIMENSION = new Dimension(2, 2);

    private Pipeline pipeline;
    private Segment tap;
    private Segment hatch;

    private final SegmentFactory factory = new SegmentFactory();

    @Override
    public Pipeline makePipeline() {
        this.pipeline = new Pipeline();

        this.makeTap();
        this.makeHatch();
        this.makePipes();

        this.pipeline.randomRotateSegments();

        return this.pipeline;
    }

    private void makeTap() {
        this.tap = this.factory.createTap();
        pipeline.addSegment(this.tap);
    }

    private void makeHatch() {
        this.hatch = this.factory.createHatch();
        pipeline.addSegment(this.hatch);
    }

    private void makePipes() {
        Field field = new Field(FIELD_DIMENSION.width, FIELD_DIMENSION.height);

        Random random = new Random();
        Point tapPos = new Point(
                random.nextInt(FIELD_DIMENSION.width),
                random.nextInt(FIELD_DIMENSION.height)
        );

        field.setSegment(tapPos, this.tap);
        this.createWinningRoute(field, tapPos);
        this.fillVoidsInFieldWithPipes(field);
        this.createNeighbourhood(field);

        for (int y = 0; y < this.FIELD_DIMENSION.height; y++) {
            for (int x = 0; x < this.FIELD_DIMENSION.width; x++) {
                this.pipeline.addSegment(field.getSegment(new Point(x, y)));
            }
        }
    }

    private void createWinningRoute(Field field, Point tapPos) {
        int routeLength = (FIELD_DIMENSION.height * FIELD_DIMENSION.width) / 2;

        Point prevPos = tapPos;
        Direction prevInputDir = null;
        PipeType prevSegmentType = null;

        for (int i = 0; i < routeLength; i++) {
            Map<Direction, Point> variants = field.getFreeNeighborsFor(prevPos);
            Map<Direction, PipeType> nextSegment = getNextPointForWinningRoute(variants, prevInputDir, prevSegmentType);

            // Если у текущей клетки нет других вариантов для дальнейшей становки, то ставим в текущую конец.
            if (nextSegment == null) {
                field.setSegment(prevPos, this.hatch);
                return;
            }

            Direction chosenDir = nextSegment.keySet().iterator().next();
            Point chosenPoint = variants.get(chosenDir);

            prevSegmentType = nextSegment.get(chosenDir);
            prevPos = chosenPoint;
            prevInputDir = chosenDir;

            field.setSegment(chosenPoint, this.factory.createPipe(prevSegmentType));
        }
        field.setSegment(prevPos, this.hatch);
    }
    private Map<Direction, PipeType> getNextPointForWinningRoute(
            Map<Direction, Point> variants, Direction prevInputDir, PipeType prevSegmentType
    ) {
        Map<Direction, PipeType> result  = new HashMap<>();
        
        Random random = new Random();

        List<Direction> keysOfVariants = new ArrayList<>(variants.keySet());

        if (prevSegmentType == null) {
            result.put(keysOfVariants.get(random.nextInt(keysOfVariants.size())), PipeType.Corner);
            return result;
        }

        int countOfTypes = PipeType.class.getEnumConstants().length;
        PipeType nextSegmentType = PipeType.class.getEnumConstants()[random.nextInt(countOfTypes)];

        if (prevSegmentType == PipeType.Adapter) {
            if (!variants.containsKey(prevInputDir)) {
                return null;
            }
            result.put(prevInputDir, nextSegmentType);
        }
        else {
            Direction nextDir;

            if (prevInputDir.equals(Direction.south()) || prevInputDir.equals(Direction.north())) {
                nextDir = variants.containsKey(Direction.east()) ? Direction.east() :
                        variants.containsKey(Direction.west()) ? Direction.west() :
                                null;
            } else {
                nextDir = variants.containsKey(Direction.south()) ? Direction.south() :
                        variants.containsKey(Direction.north()) ? Direction.north() :
                                null;
            }
            result.put(nextDir, nextSegmentType);
        }
        return result;
    }

    private void fillVoidsInFieldWithPipes(Field field) {
        Random random = new Random();
        int countOfTypes = PipeType.class.getEnumConstants().length;

        for (int i = 0; i < this.FIELD_DIMENSION.height; i++) {
            for (int j = 0; j < this.FIELD_DIMENSION.width; j++) {

                PipeType nextSegmentType = PipeType.class.getEnumConstants()[random.nextInt(countOfTypes)];
                Point p = new Point(i, j);

                if (field.getSegment(p) == null) {
                    field.setSegment(p, this.factory.createPipe(nextSegmentType));
                }
            }
        }
    }

    private void createNeighbourhood(Field field) {
        for (int y = 0; y < this.FIELD_DIMENSION.height; y++) {
            for (int x = 0; x < this.FIELD_DIMENSION.width; x++) {
                int finalX = x;
                int finalY = y;
                Map<Direction, Point> neighborsByDirections  = new HashMap<>() {{
                    put(Direction.west(), new Point(finalX - 1, finalY));
                    put(Direction.east(), new Point(finalX + 1, finalY));
                    put(Direction.north(), new Point(finalX, finalY - 1));
                    put(Direction.south(), new Point(finalX, finalY + 1));
                }};

                Segment currentSegment = field.getSegment(new Point(x, y));
                for (Map.Entry<Direction, Point> entry : neighborsByDirections.entrySet()) {
                    Direction direction = entry.getKey();
                    Point point = entry.getValue();

                    Segment neighbor = field.getSegment(point);
                    if (neighbor == null) continue;

                    if (Direction.east().equals(direction)) {
                        currentSegment.rightNeighbor = neighbor;
                    } else if (Direction.west().equals(direction)) {
                        currentSegment.leftNeighbor = neighbor;
                    } else if (Direction.south().equals(direction)) {
                        currentSegment.bottomNeighbor = neighbor;
                    } else if (Direction.north().equals(direction)) {
                        currentSegment.topNeighbor = neighbor;
                    }
                }
            }
        }
    }
}
