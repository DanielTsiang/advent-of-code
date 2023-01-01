/**
 * Script to determine positions visited by the tail end of a rope being pulled by the head end.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.Coordinate;
import utils.Day;
import utils.Day09Instruction;
import utils.Day09Movement;

public class Day09 extends Day {
    List<Day09Instruction> data;

    private Day09() {
        this.data = this.parseData();
    }

    private List<Day09Instruction> parseData() {
        List<Day09Instruction> data;
        String string;
        String[] row;
        Day09Instruction instruction;

        // Read each line of input data as a Day09Instruction, and store inside List
        data = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            string = this.scanner.nextLine();
            row = string.split(" ");
            instruction = new Day09Instruction(row[0], row[1]);
            data.add(instruction);
        }

        return data;
    }

    private boolean isTouching(Coordinate knot1, Coordinate knot2) {
        // Check if 2 knots are touching
        int xDistance = Math.abs(knot1.getX() - knot2.getX());
        int yDistance = Math.abs(knot1.getY() - knot2.getY());
        return xDistance <= 1 && yDistance <= 1;
    }

    private int getTailMovement(int distance) {
        // Get direction of movement of the tail knot in x or y direction
        return Integer.compare(distance, 0);
    }

    private Coordinate getNewTailPosition(Coordinate tailPosition, Coordinate headPosition) {
        Coordinate direction;
        int xDistance;
        int yDistance;

        if (this.isTouching(tailPosition, headPosition)) {
            return tailPosition;
        }

        // Calculate direction of movement of the tail knot
        direction = headPosition.subtract(tailPosition);

        // Calculate new tail position
        xDistance = this.getTailMovement(direction.getX());
        yDistance = this.getTailMovement(direction.getY());
        return tailPosition.add(new Coordinate(xDistance, yDistance));
    }

    private Coordinate getNewHeadPosition(Coordinate headPosition, Day09Instruction instruction) {
        switch (instruction.getDirection()) {
            case "R":
                return headPosition.add(new Day09Movement().getRight());
            case "L":
                return headPosition.add(new Day09Movement().getLeft());
            case "U":
                return headPosition.add(new Day09Movement().getUp());
            case "D":
                return headPosition.add(new Day09Movement().getDown());
            default:
                throw new IllegalArgumentException("Unknown direction: " + instruction.getDirection());
        }
    }

    private int calculateTailPositionsVisited(int numKnots) {
        // Determine number of positions visited by the tail end of a rope being pulled by the head end,
        // with all knots starting at the origin
        List<Coordinate> knotPositions;
        Set<Coordinate> tailPositionsVisited;
        int knotPositionsLastIndex;

        // Initialise the knot positions and positions visited set
        knotPositions = new ArrayList<>();
        for (int i = 0; i < numKnots; i++) {
            knotPositions.add(new Coordinate(0, 0));
        }
        tailPositionsVisited = new HashSet<>();
        tailPositionsVisited.add(new Coordinate(0, 0));

        for (Day09Instruction instruction : this.data) {
            for (int i = 0; i < instruction.getDistance(); i++) {
                // Determine new head position one step at a time
                knotPositionsLastIndex = knotPositions.size() - 1;
                knotPositions.set(knotPositionsLastIndex, this.getNewHeadPosition(knotPositions.get(knotPositionsLastIndex), instruction));

                for (int j = knotPositions.size() - 1; j >= 1; j--) {
                    // Determine new knot positions after the head one step at a time.
                    // Adjacent knots must always be touching.
                    knotPositions.set(j - 1, this.getNewTailPosition(knotPositions.get(j - 1), knotPositions.get(j)));
                }
                // Update the positions visited by the tail knot set
                tailPositionsVisited.add(knotPositions.get(0));
            }
        }
        return tailPositionsVisited.size();
    }

    private int solveOne() {
        // Determine number of positions visited by the tail end of a rope being pulled by the head end,
        // with both ends starting at the origin, i.e. 2 knots
        int numKnots = 2;
        return this.calculateTailPositionsVisited(numKnots);
    }

    private int solveTwo() {
        // Determine number of positions visited by the tail end of a rope being pulled by the head end,
        // with all 10 knots starting at the origin
        int numKnots = 10;
        return this.calculateTailPositionsVisited(numKnots);
    }

    public static void main(String[] args) {
        Day09 day09;
        int partOnePositionsVisited;
        int partTwoPositionsVisited;

        day09 = new Day09();

        partOnePositionsVisited = day09.solveOne();
        System.out.println("part one solution: " + partOnePositionsVisited);

        partTwoPositionsVisited = day09.solveTwo();
        System.out.println("part two solution: " + partTwoPositionsVisited);
    }
}
