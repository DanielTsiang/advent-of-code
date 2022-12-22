/**
 * Script to find the total score for the two-letter combinations.
 */

import utils.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Day02 extends Day {

    protected List<String> parseData() {
        String value;
        List<String> values;

        // Read each line of input data as String, and store inside List
        values = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            value = this.scanner.nextLine();
            values.add(value);
        }

        return values;
    }

    private int solveOne(List<String> values) {
        int totalScore;
        Map<String, Integer> playMap;

        // Find the total score for each row of two-letter combinations
        playMap = Map.of(
          "A X", 4,
          "A Y", 8,
          "A Z", 3,
          "B X", 1,
          "B Y", 5,
          "B Z", 9,
          "C X", 7,
          "C Y", 2,
          "C Z", 6
        );

        totalScore = 0;
        for (String value : values) {
            totalScore += playMap.get(value);
        }

        return totalScore;
    }

    private int solveTwo(List<String> values) {
        int totalScore;
        Map<String, Integer> playMap;

        // Find the total score for each row of two-letter combinations
        playMap = Map.of(
          "A X", 3,
          "A Y", 4,
          "A Z", 8,
          "B X", 1,
          "B Y", 5,
          "B Z", 9,
          "C X", 2,
          "C Y", 6,
          "C Z", 7
        );

        totalScore = 0;
        for (String value : values) {
            totalScore += playMap.get(value);
        }

        return totalScore;
    }

    public static void main(String[] args) {
        Day02 day02;
        List<String> values;
        int partOneScore;
        int partTwoScore;

        day02 = new Day02();
        values = day02.parseData();

        partOneScore = day02.solveOne(values);
        System.out.println("part one solution: " + partOneScore);

        partTwoScore = day02.solveTwo(values);
        System.out.println("part two solution: " + partTwoScore);
    }
}
