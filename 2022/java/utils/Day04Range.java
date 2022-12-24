package utils;

public class Day04Range {
    private final int start;
    private final int end;

    public Day04Range(String[] range) {
        this.start = Integer.parseInt(range[0]);
        this.end = Integer.parseInt(range[1]);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
