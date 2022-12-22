/**
 * Script to find the total of the top 3 sums, where each block of numbers form a sum.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Day;


public class Day01 extends Day {

    private List<String> parseData() {
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
        int maxValue;
        int counter;

        // Find the max sum, where each block of numbers form a sum
        maxValue = 0;
        counter = 0;
        for (String value : values) {
            if (!value.equals("")) {
                counter += Integer.parseInt(value);
                if (counter > maxValue) {
                    maxValue = counter;
                }
            } else {
                counter = 0;
            }
        }

        return maxValue;
    }

    private int solveTwo(List<String> values) {
        List<Integer> sums;
        int counter;

        // Find the total of the top 3 sums, where each block of numbers form a sum
        sums = new ArrayList<>();
        counter = 0;
        for (String value : values) {
            if (!value.equals("")) {
                counter += Integer.parseInt(value);
            } else {
                sums.add(counter);
                counter = 0;
            }
        }

        // Sort the sums List in descending order
        sums.sort(Collections.reverseOrder());

        // Return the total of the top 3 sums
        return sums.get(0) + sums.get(1) + sums.get(2);
    }

    public static void main(String[] args) {
        Day01 day01;
        List<String> values;
        int topSum;
        int top3Sums;

        day01 = new Day01();
        values = day01.parseData();

        topSum = day01.solveOne(values);
        System.out.println("part one solution: " + topSum);

        top3Sums = day01.solveTwo(values);
        System.out.println("part two solution: " + top3Sums);
    }
}
