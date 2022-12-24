/**
 * Script to find overlapping ranges.
 */
import java.util.ArrayList;
import java.util.List;

import utils.Day04Range;
import utils.Day;

public class Day04 extends Day {

    private List<Day04Range[]> parseData() {
        String line;
        List<Day04Range[]> data;
        String[] rangeStrings;
        Day04Range[] ranges;
        Day04Range range1;
        Day04Range range2;

        // Parse each line of input data into array of Day04Range, and store inside List
        data = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            line = this.scanner.nextLine();
            rangeStrings = line.split(",");
            range1 = new Day04Range(rangeStrings[0].split("-"));
            range2 = new Day04Range(rangeStrings[1].split("-"));
            ranges = new Day04Range[]{range1, range2};
            data.add(ranges);
        }

        return data;
    }

    private int solveOne(List<Day04Range[]> data) {
        int subsetCount;
        Day04Range range1;
        Day04Range range2;

        // Find total count of complete overlaps, where one range is a subset of another range
        subsetCount = 0;
        for (Day04Range[] ranges : data) {
            range1 = ranges[0];
            range2 = ranges[1];
            // Check if range1 is a subset of range2
            if (range1.getStart() >= range2.getStart() && range1.getEnd() <= range2.getEnd()) {
                subsetCount++;
            // Check if range 2 is a subset of range1
            } else if (range2.getStart() >= range1.getStart() && range2.getEnd() <= range1.getEnd()) {
                subsetCount++;
            }
        }

        return subsetCount;
    }

    private int solveTwo(List<Day04Range[]> data) {
        int overlapCount;
        Day04Range range1;
        Day04Range range2;

        // Find total count of partial overlaps, where one range overlaps another range
        overlapCount = 0;
        for (Day04Range[] ranges : data) {
            range1 = ranges[0];
            range2 = ranges[1];
            // Check if ranges overlap
            if (range1.getStart() <= range2.getEnd() && range1.getEnd() >= range2.getStart()) {
                overlapCount++;
            }
        }

        return overlapCount;
    }

    public static void main(String[] args) {
        Day04 day04;
        List<Day04Range[]> data;
        int subsetCount;
        int overlapCount;

        day04 = new Day04();
        data = day04.parseData();

        subsetCount = day04.solveOne(data);
        System.out.println("part one solution: " + subsetCount);

        overlapCount = day04.solveTwo(data);
        System.out.println("part two solution: " + overlapCount);
    }
}
