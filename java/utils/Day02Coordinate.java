package utils;

// class to store position, depth and optionally aim coordinates
public class Day02Coordinate {
    private int position;
    private int depth;
    private int aim;

    public Day02Coordinate(int position, int depth, int aim) {
        this.position = position;
        this.depth = depth;
        this.aim = aim;
    }

    public Day02Coordinate(int position, int depth) {
        this.position = position;
        this.depth = depth;
    }

    public int getPosition() {
        return position;
    }

    public int getDepth() {
        return depth;
    }

    public int getAim() {
        return aim;
    }

    public void setPosition(int newPosition) {
        this.position = newPosition;
    }

    public void setDepth(int newDepth) {
        this.depth = newDepth;
    }

    public void setAim(int newAim) {
        this.aim = newAim;
    }
}
