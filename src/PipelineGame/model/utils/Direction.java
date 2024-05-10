package PipelineGame.model.utils;

public class Direction {
    private final int _hours;

    private Direction(int hours) {
        hours = hours % 12;
        if (hours < 0) {
            hours += 12;
        }
        _hours = hours;
    }

    public static Direction north() {
        return new Direction(0);
    }

    public static Direction south() {
        return new Direction(6);
    }

    public static Direction east() {
        return new Direction(3);
    }

    public static Direction west() {
        return new Direction(9);
    }

    public Direction clockwise() {
        return new Direction(this._hours + 3);
    }

    public Direction opposite() {
        return new Direction(this._hours + 6);
    }

    public boolean isOpposite(Direction other) {
        return this.opposite().equals(other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Direction direct)) {
            return false;
        }
        return _hours == direct._hours;
    }

    @Override
    public int hashCode() {
        return this._hours;
    }

    @Override
    public String toString() {
        String msg = "";

        if(_hours == 0) {
            msg = "N";
        } else if(_hours == 3) {
            msg = "E";
        } else if (_hours == 6) {
            msg = "S";
        } else if(_hours == 9) {
            msg = "W";
        }

        return msg;
    }
}
