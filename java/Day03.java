/**
 * Script to find the most common bit in the corresponding position of all binary numbers in text file.
 * Bit criteria is also used to filter out binary numbers until one number remains.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.Day03BitCounter;
import utils.Day;

public class Day03 extends Day {
    private List<String> parseData() {
        String binaryNumber;
        List<String> binaryNumbers;

        // Read each line of input data as String, and store inside List
        binaryNumbers = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            binaryNumber = this.scanner.next();
            binaryNumbers.add(binaryNumber);
        }

        return binaryNumbers;
    }

    private Day03BitCounter[] countBits(int binaryNumberLength, List<String> binaryNumbers) {
        Day03BitCounter[] counts;
        char bit;

        // Store Day03BitCounter objects in array
        counts = new Day03BitCounter[binaryNumberLength];
        for (int i = 0; i < binaryNumberLength; i++) {
            counts[i] = new Day03BitCounter();
        }

        // Count bits for each binary number
        for (String binaryNumber : binaryNumbers) {
            for (int i = 0; i < binaryNumberLength; i++) {
                bit = binaryNumber.charAt(i);
                switch (bit) {
                    case '0' -> counts[i].setZeros(counts[i].getZeros() + 1);
                    case '1' -> counts[i].setOnes(counts[i].getOnes() + 1);
                    default -> throw new IllegalArgumentException(
                        bit + "is an invalid bit."
                    );
                }
            }
        }

        return counts;
    }

    private int solveOne(List<String> binaryNumbers) {
        int binaryNumberLength;
        Day03BitCounter[] counts;
        int gammaRate;
        int epsilonRate;

        // Get Array for counts of each bit index
        binaryNumberLength = binaryNumbers.get(0).length();
        counts = countBits(binaryNumberLength, binaryNumbers);

        // Gamma rate and epsilon rate are determined using most and least common bit count, respectively
        StringBuilder binaryGammaRate = new StringBuilder();
        StringBuilder binaryEpsilonRate = new StringBuilder();
        for (int i = 0; i < binaryNumberLength; i++) {
            if (counts[i].getZeros() > counts[i].getOnes()) {
                binaryGammaRate.append('0');
                binaryEpsilonRate.append('1');
            } else {
                binaryGammaRate.append('1');
                binaryEpsilonRate.append('0');
            }
        }

        // Convert binary gamma and epsilon rates (in String form) from binary numbers (base 2) to decimal numbers
        gammaRate = Integer.parseInt(binaryGammaRate.toString(), 2);
        epsilonRate = Integer.parseInt(binaryEpsilonRate.toString(), 2);

        // Multiply two rates together to get power consumption
        return gammaRate * epsilonRate;
    }

    private int solveTwo(List<String> binaryNumbers) {
        int binaryNumberLength;
        List<String> oxygenData;
        List<String> co2Data;
        Day03BitCounter[] oxygenCounts;
        Day03BitCounter[] co2Counts;
        char oxygenMostCommon;
        char co2LeastCommon;
        int oxygenGeneratorRating;
        int co2ScrubberRating;

        // Get length of binary numbers
        binaryNumberLength = binaryNumbers.get(0).length();

        // Find one binary number that matches bit criteria
        // Oxygen generator and CO2 scrubber ratings use most and least common value in bit position, respectively
        oxygenData = new ArrayList<>(binaryNumbers);
        co2Data = new ArrayList<>(binaryNumbers);
        for (int i = 0; i < binaryNumberLength; i++) {
            // Calculate bit counts for each iteration's dataset
            oxygenCounts = countBits(binaryNumberLength, oxygenData);
            co2Counts = countBits(binaryNumberLength, co2Data);

            // Get most & least common bits for oxygen and co2 datasets at required index, respectively
            oxygenMostCommon = oxygenCounts[i].getZeros() > oxygenCounts[i].getOnes() ? '0' : '1';
            co2LeastCommon = co2Counts[i].getZeros() > co2Counts[i].getOnes() ? '1' : '0';

            if (oxygenData.size() > 1) {
                // Filter List to keep only binary numbers that have the most common bit at required index
                int index = i;
                char mostCommonBit = oxygenMostCommon;
                oxygenData = oxygenData.stream()
                    .filter(binaryNumber -> binaryNumber.charAt(index) == mostCommonBit)
                    .collect(Collectors.toList());
            }

            if (co2Data.size() > 1) {
                // Filter List to keep only binary numbers that have the least common bit at required index
                int index = i;
                char leastCommonBit = co2LeastCommon;
                 co2Data = co2Data.stream()
                    .filter(binaryNumber -> binaryNumber.charAt(index) == leastCommonBit)
                    .collect(Collectors.toList());
            }

            if (oxygenData.size() == 1 && co2Data.size() == 1) {
                break;
            }
        }

        oxygenGeneratorRating = Integer.parseInt(oxygenData.get(0), 2);
        co2ScrubberRating = Integer.parseInt(co2Data.get(0), 2);

        // Multiply two ratings together to get life support rating
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    public static void main(String[] args) {
        Day03 day03;
        List<String> binaryNumbers;
        int powerConsumption;
        int lifeSupportRating;

        day03 = new Day03();
        binaryNumbers = day03.parseData();

        powerConsumption = day03.solveOne(binaryNumbers);
        System.out.println("part one solution: " + powerConsumption);

        lifeSupportRating = day03.solveTwo(binaryNumbers);
        System.out.println("part two solution: " + lifeSupportRating);
    }
}
