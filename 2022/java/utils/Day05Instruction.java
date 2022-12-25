package utils;

public class Day05Instruction {
    private final int amount;
    private final int start;
    private final int end;

    public Day05Instruction(String amount, String start, String end) {
        this.amount = Integer.parseInt(amount);
        this.start = Integer.parseInt(start);
        this.end = Integer.parseInt(end);
    }

    public int getAmount() {
        return amount;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
