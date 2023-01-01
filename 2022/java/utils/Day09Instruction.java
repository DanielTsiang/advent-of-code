package utils;

public class Day09Instruction {
    private final String direction;
    private final int distance;

    public Day09Instruction(String direction, String distance) {
        this.direction = direction;
        this.distance = Integer.parseInt(distance);
    }

    public String getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
