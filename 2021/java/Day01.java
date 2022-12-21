/**
 * Script to count the number of times a depth measurement increases from the previous measurement,
 * when considering one-measurement & three-measurement sliding windows.
 */

import java.util.ArrayList;
import java.util.List;
import utils.Day;


public class Day01 extends Day {
    private List<Integer> parseData() {
        List<Integer> depths;
        int depth;

        // Read input data into List
        depths = new ArrayList<>();
        while (this.scanner.hasNextInt()) {
            depth = this.scanner.nextInt();
            depths.add(depth);
        }

        return depths;
    }

    private int solveOne(List<Integer> depths) {
        int counter;

        // Loop through List and increment counter if depth measurement is higher than previous
        counter = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                counter++;
            }
        }

        return counter;
    }

    private int solveTwo(List<Integer> depths) {
        int counter;

        // Make use of the fact that for array {a, b, c, d},
        // (b + c + d) is greater than (a + b + c) if d > a
        counter = 0;
        for (int i = 3; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 3)) {
                counter++;
            }
        }

        return counter;
    }

    public static void main(String[] args) {
        Day01 day01;
        List<Integer> depths;
        int counterOne;
        int counterTwo;

        day01 = new Day01();
        depths = day01.parseData();

        counterOne = day01.solveOne(depths);
        System.out.println("part one solution: " + counterOne);

        counterTwo = day01.solveTwo(depths);
        System.out.println("part two solution: " + counterTwo);
    }
}
