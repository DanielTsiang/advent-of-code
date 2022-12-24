/**
 * Script to find values of common items.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import utils.Day;


public class Day03 extends Day {
    private final Map<Character, Integer> lowercaseMap;
    private final Map<Character, Integer> uppercaseMap;

    private Day03() {
        this.lowercaseMap = this.getLowercaseMap();
        this.uppercaseMap = this.getUppercaseMap();
    }

    private Map<Character, Integer> getLowercaseMap() {
        Map<Character, Integer> lowercaseMap;

        lowercaseMap = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            lowercaseMap.put(c, (int) c - 96);
        }

        return lowercaseMap;
    }

    private Map<Character, Integer> getUppercaseMap() {
        Map<Character, Integer> uppercaseMap;

        uppercaseMap = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            uppercaseMap.put(c, (int) c - 38);
        }

        return uppercaseMap;
    }

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
        int midpoint;
        int totalValue;
        String firstHalf;
        String secondHalf;
        Set<Character> firstSet;
        Set<Character> secondSet;
        Character letter;

        // Find the total value of common items, i.e. letters which appear in both halves of the string
        totalValue = 0;
        for (String value : values) {
            midpoint = value.length() / 2;
            firstHalf = value.substring(0, midpoint);
            secondHalf = value.substring(midpoint);

            firstSet = firstHalf.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
            secondSet = secondHalf.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());

            // Find common letter between the two sets
            firstSet.retainAll(secondSet);
            letter = firstSet.iterator().next();

            if (Character.isLowerCase(letter)) {
                totalValue += this.lowercaseMap.get(letter);
            } else {
                totalValue += this.uppercaseMap.get(letter);
            }
        }

        return totalValue;
    }

    private int solveTwo(List<String> values) {
        int totalValue;
        String string1;
        String string2;
        String string3;
        Set<Character> firstSet;
        Set<Character> secondSet;
        Set<Character> thirdSet;
        Character letter;

        // Find the total value of common items, i.e. letters which appear in each group of 3 strings
        totalValue = 0;
        for (int i = 0; i < values.size(); i += 3) {
            string1 = values.get(i);
            string2 = values.get(i + 1);
            string3 = values.get(i + 2);

            firstSet = string1.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
            secondSet = string2.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
            thirdSet = string3.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());

            // Find common letter between the two sets
            firstSet.retainAll(secondSet);
            firstSet.retainAll(thirdSet);
            letter = firstSet.iterator().next();

            if (Character.isLowerCase(letter)) {
                totalValue += this.lowercaseMap.get(letter);
            } else {
                totalValue += this.uppercaseMap.get(letter);
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        Day03 day03;
        List<String> strings;
        int partOneValue;
        int partTwoValue;

        day03 = new Day03();
        strings = day03.parseData();

        partOneValue = day03.solveOne(strings);
        System.out.println("part one solution: " + partOneValue);

        partTwoValue = day03.solveTwo(strings);
        System.out.println("part two solution: " + partTwoValue);
    }
}
