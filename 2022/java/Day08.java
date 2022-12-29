/**
 * Script to determine number of trees not hidden by other trees and their scenic scores.
 */

import java.util.ArrayList;
import java.util.List;

import utils.Day;

public class Day08 extends Day {
    List<List<Integer>> data;
    int rows;
    int columns;
    private Day08() {
        this.data = this.parseData();
        this.rows = this.data.size();
        this.columns = this.data.get(0).size();
    }

    private List<List<Integer>> parseData() {
        List<List<Integer>> data;
        String string;
        List<Integer> values;

        // Read each line of input data as List of Integers, and store inside List
        data = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            values = new ArrayList<>();
            string = this.scanner.nextLine();
            for (int i = 0; i < string.length(); i++){
                char c = string.charAt(i);
                values.add(Character.getNumericValue(c));
            }
            data.add(values);
        }

        return data;
    }

    private int solveOne() {
        // Find number of visible trees, i.e. trees that are taller than any other tree in any 4 directions
        int totalVisibleTrees = 0;
        int externalVisibleTrees;
        int height;
        int maxHeightRight;
        int maxHeightLeft;
        int maxHeightUp;
        int maxHeightDown;

        // Find external visible trees
        externalVisibleTrees = 2 * this.rows + 2 * this.columns - 4;
        totalVisibleTrees += externalVisibleTrees;

        // Find internal visible trees
        for (int i = 1; i < this.rows - 1; i++) {
            for (int j = 1; j < this.columns - 1; j++) {
                height = this.data.get(i).get(j);

                // Find the tallest tree to the right
                maxHeightRight = 0;
                for (int k = j + 1; k < this.columns; k++) {
                    if (this.data.get(i).get(k) > maxHeightRight) {
                        maxHeightRight = this.data.get(i).get(k);
                    }
                }

                // Find the tallest tree to the left
                maxHeightLeft = 0;
                for (int k = j - 1; k >= 0; k--) {
                    if (this.data.get(i).get(k) > maxHeightLeft) {
                        maxHeightLeft = this.data.get(i).get(k);
                    }
                }

                // Find the tallest tree above
                maxHeightUp = 0;
                for (int k = i - 1; k >= 0; k--) {
                    if (this.data.get(k).get(j) > maxHeightUp) {
                        maxHeightUp = this.data.get(k).get(j);
                    }
                }

                // Find the tallest tree below
                maxHeightDown = 0;
                for (int k = i + 1; k < this.rows; k++) {
                    if (this.data.get(k).get(j) > maxHeightDown) {
                        maxHeightDown = this.data.get(k).get(j);
                    }
                }

                // Check if tree is visible if any 4 directions
                if (height > maxHeightRight || height > maxHeightLeft || height > maxHeightUp || height > maxHeightDown) {
                    totalVisibleTrees++;
                }
            }
        }

        return totalVisibleTrees;
    }

    private int computeViewingDistance(int currentTree, List<Integer> trees) {
        int viewingDistance = 0;
        for (int tree : trees) {
            viewingDistance++;
            if (tree >= currentTree) {
                break;
            }
        }
        return viewingDistance;
    }

    private int solveTwo() {
        // Find the tree with the highest scenic score
        List<Integer> trees;
        List<Integer> reversedTrees;
        int height;
        int viewingDistanceRight;
        int viewingDistanceLeft;
        int viewingDistanceDown;
        int viewingDistanceUp;
        int scenicScore;
        int maxScenicScore = 0;

        // Find the scenic score of each tree, i.e. its viewing distance multiplied together in each of the 4 directions
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                height = this.data.get(i).get(j);

                // Find the viewing distance to the right
                trees = this.data.get(i).subList(j + 1, this.columns);
                viewingDistanceRight = this.computeViewingDistance(height, trees);

                // Find the viewing distance to the left
                trees = this.data.get(i).subList(0, j);
                // Reverse order to check towards the left from the current tree
                reversedTrees = new ArrayList<>();
                for (int k = trees.size() - 1; k >= 0; k--) {
                    reversedTrees.add(trees.get(k));
                }
                viewingDistanceLeft = this.computeViewingDistance(height, reversedTrees);

                // Find the viewing distance above
                trees = new ArrayList<>();
                for (int k = i - 1; k >= 0; k--) {
                    trees.add(this.data.get(k).get(j));
                }
                viewingDistanceUp = this.computeViewingDistance(height, trees);

                // Find the viewing distance below
                trees = new ArrayList<>();
                for (int k = i + 1; k < this.rows; k++) {
                    trees.add(this.data.get(k).get(j));
                }
                viewingDistanceDown = this.computeViewingDistance(height, trees);

                // Calculate and set scenic score if it is the highest so far
                scenicScore = viewingDistanceRight * viewingDistanceLeft * viewingDistanceUp * viewingDistanceDown;
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }

        return maxScenicScore;
    }

    public static void main(String[] args) {
        Day08 day08;
        int totalVisibleTrees;
        int maxScenicScore;

        day08 = new Day08();

        totalVisibleTrees = day08.solveOne();
        System.out.println("part one solution: " + totalVisibleTrees);

        maxScenicScore = day08.solveTwo();
        System.out.println("part two solution: " + maxScenicScore);
    }
}
