/**
 * Script to calculate the submarine horizontal position, depth and optionally aim
 * after following a planned course.
 */

import java.util.ArrayList;
import java.util.List;
import utils.Day02Coordinate;
import utils.Day02Instruction;
import utils.Day;


public class Day02 extends Day {
    private List<Day02Instruction> parseData() {
        Day02Instruction instruction;
        List<Day02Instruction> instructions;
        String direction;
        int magnitude;

        // Read each line of input data into Day02Instruction record, and store each inside List
        instructions = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            direction = this.scanner.next();
            magnitude = this.scanner.nextInt();

            instruction = new Day02Instruction(direction, magnitude);
            instructions.add(instruction);
        }

        return instructions;
    }

    private int solveOne(List<Day02Instruction> instructions) {
        String direction;
        int magnitude;
        Day02Coordinate coordinate;
        int multipliedCoordinate;

        // Calculate horizontal position and depth after following the planned course
        coordinate = new Day02Coordinate(0, 0);

        for (Day02Instruction instruction : instructions) {
            direction = instruction.direction();
            magnitude = instruction.magnitude();

            switch (direction) {
                case "forward" -> coordinate.setPosition(coordinate.getPosition() + magnitude);
                case "down" -> coordinate.setDepth(coordinate.getDepth() + magnitude);
                case "up" -> coordinate.setDepth(coordinate.getDepth() - magnitude);
                default -> throw new IllegalArgumentException(
                    "Direction '" + direction + "' not recognized."
                );
            }
        }

        // Multiply final horizontal position by final depth
        multipliedCoordinate = coordinate.getPosition() * coordinate.getDepth();
        return multipliedCoordinate;
    }

    private int solveTwo(List<Day02Instruction> instructions) {
        String direction;
        int magnitude;
        Day02Coordinate coordinate;
        int multipliedCoordinate;

        // Calculate horizontal position, depth and aim after following the planned course
        coordinate = new Day02Coordinate(0, 0, 0);

        for (Day02Instruction instruction : instructions) {
            direction = instruction.direction();
            magnitude = instruction.magnitude();

            switch (direction) {
                case "forward" -> {
                    coordinate.setPosition(coordinate.getPosition() + magnitude);
                    coordinate.setDepth(coordinate.getDepth() + (coordinate.getAim() * magnitude));
                }
                case "down" -> coordinate.setAim(coordinate.getAim() + magnitude);
                case "up" -> coordinate.setAim(coordinate.getAim() - magnitude);
                default -> throw new IllegalArgumentException(
                    "Direction '" + direction + "' not recognized."
                );
            }
        }

        // Multiply final horizontal position by final depth
        multipliedCoordinate = coordinate.getPosition() * coordinate.getDepth();
        return multipliedCoordinate;
    }

    public static void main(String[] args) {
        Day02 day02;
        List<Day02Instruction> instructions;
        int coordinatesOne;
        int coordinatesTwo;

        day02 = new Day02();
        instructions = day02.parseData();

        coordinatesOne = day02.solveOne(instructions);
        System.out.println("part one solution: " + coordinatesOne);

        coordinatesTwo = day02.solveTwo(instructions);
        System.out.println("part two solution: " + coordinatesTwo);
    }
}
