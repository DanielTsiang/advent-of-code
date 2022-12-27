/**
 * Script to detect markers in datastream.
 */

import utils.Day;


public class Day06 extends Day {

    private String parseData() {
        String datastream = "";

        // Read input datastream as String
        while (this.scanner.hasNextLine()) {
            datastream = this.scanner.nextLine();
        }

        return datastream;
    }

    private int solveOne(String datastream) {
        int distinctChars;

        // Find number of characters that need to be processed before the first start-of-packet marker is detected,
        // i.e. 4 unique characters in a row have been detected
        distinctChars = 4;
        for (int i = distinctChars; i < datastream.length(); i++) {
            if (datastream.substring(i - distinctChars, i).chars().distinct().count() == distinctChars) {
                return i;
            }
        }
        return 0;
    }

    private int solveTwo(String datastream) {
        int distinctChars;

        // Find number of characters that need to be processed before the first start-of-packet marker is detected,
        // i.e. 14 unique characters in a row have been detected
        distinctChars = 14;
        for (int i = distinctChars; i < datastream.length(); i++) {
            if (datastream.substring(i - distinctChars, i).chars().distinct().count() == distinctChars) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Day06 day06;
        String datastream;
        int startOfPacketMarker;
        int startOfMessageMarker;

        day06 = new Day06();
        datastream = day06.parseData();

        startOfPacketMarker = day06.solveOne(datastream);
        System.out.println("part one solution: " + startOfPacketMarker);

        startOfMessageMarker = day06.solveTwo(datastream);
        System.out.println("part two solution: " + startOfMessageMarker);
    }
}
