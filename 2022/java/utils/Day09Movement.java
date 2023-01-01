package utils;

public class Day09Movement {
    public Coordinate getRight() {
        return new Coordinate(1, 0);
    }

    public Coordinate getLeft() {
        return new Coordinate(-1, 0);
    }

    public Coordinate getUp() {
        return new Coordinate(0, 1);
    }

    public Coordinate getDown() {
        return new Coordinate(0, -1);
    }
}
