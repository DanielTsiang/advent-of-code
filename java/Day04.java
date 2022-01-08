/**
 * Script to find the first and last winning Bingo scores from multiple boards.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Day;
import utils.Day04Board;
import utils.Day04BoardsData;


public class Day04 extends Day {

    private Day04BoardsData parseData() {
        int rowCounter;
        int rowLength;
        Day04Board board;
        List<List<Integer>> boardList;
        List<Day04Board> boardsRows;
        String numbersRow;
        List<Integer> numbersToDraw;
        List<Integer> row;

        // Initialize variables
        rowCounter = 0;
        rowLength = 0;
        boardList = new ArrayList<>();
        boardsRows = new ArrayList<>();
        numbersToDraw = new ArrayList<>();

        // Read each line of input data as String, trim whitespace, split String into
        // array of integers and store inside List
        while (this.scanner.hasNextLine()) {
            numbersRow = this.scanner.nextLine();

            // Save first line of data as List of Integers
            if (rowCounter == 0) {
                numbersToDraw = Arrays.stream(
                    numbersRow
                        .split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toList();
            }

            // Save non-empty rows inside List
            else if (!numbersRow.equals("")) {
                // Trim whitespace, then split String into separate Integers
                row = Arrays.stream(
                    numbersRow
                        .trim()
                        .split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toList();

                // Get length of board rows if unset
                rowLength = rowLength == 0 ? row.size() : rowLength;

                // Add board rows to square board to get 2D String List
                if (boardList.size() < rowLength) {
                    boardList.add(row);
                }

                // Add complete 'board' to 'boardsRows' List, and reset 'boardList' List to empty
                if (boardList.size() == rowLength) {
                    board = new Day04Board(boardList);
                    boardsRows.add(board);
                    boardList = new ArrayList<>();
                }
            }
            rowCounter++;
        }

        return new Day04BoardsData(numbersToDraw, boardsRows);
    }

    private boolean checkBoard(Day04Board board, List<Integer> numbersDrawn, int length) {
        List<Boolean> rowInNumbersDrawn;
        List<Boolean> columnInNumbersDrawn;

        for (int i = 0; i < length; i++) {
            // Check row
            rowInNumbersDrawn = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                if (numbersDrawn.contains(board.rows().get(i).get(j))) {
                    rowInNumbersDrawn.add(true);
                }
            }
            if (rowInNumbersDrawn.size() == length) {
                return true;
            }

            // Check column
            columnInNumbersDrawn = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                if (numbersDrawn.contains(board.rows().get(j).get(i))) {
                    columnInNumbersDrawn.add(true);
                }
            }
            if (columnInNumbersDrawn.size() == length) {
                return true;
            }
        }

        // Not a winning board
        return false;
    }

    private Map<Integer, Integer> getWinningBoards(Day04BoardsData boardsData) {
        Map<Integer, Integer> order;
        int length;
        Day04Board board;

        // Initialize Map to store mapping of winning Board indices to last number drawn indices
        order = new HashMap<>();

        // Loop through numbers to draw, starting from minimum length to win
        length = boardsData.boards().get(0).rows().size();
        for (int i = length; i < boardsData.numbersToDraw().size(); i++) {
            // Loop through boards
            for (int j = 0; j < boardsData.boards().size(); j++) {
                board = boardsData.boards().get(j);
                // Check if already seen winning board before
                if (order.containsKey(j)) {
                    continue;
                }

                // Check if winning board
                if (checkBoard(board, boardsData.numbersToDraw().subList(0, i), length)) {
                    // Put winning Board index and last number drawn index into `order` Map
                    order.put(j, i);
                }
            }
        }

        // Return mapping of winning Board indices to last number drawn indices
        return order;
    }

    private int getScore(Day04Board board, List<Integer> numbersDrawn) {
        int length;
        int unmarkedSum;
        int boardNumber;

        // Initialize variables
        length = board.rows().size();
        unmarkedSum = 0;

        // Sum numbers in winning board that did not match numbers drawn
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                boardNumber = board.rows().get(i).get(j);
                if (!numbersDrawn.contains(boardNumber)) {
                    unmarkedSum += boardNumber;
                }
            }
        }

        // Return final score
        return unmarkedSum * numbersDrawn.get(numbersDrawn.size() - 1);
    }

    private int solveOne(Day04BoardsData boardsData, Map<Integer, Integer> order) {
        int firstWinningBoardIndex;
        int lastNumberDrawn;

        // Get key with minimum value in Map
        // i.e. first winning board index which has the lowest number drawn index
        firstWinningBoardIndex = Collections.min(
            order.entrySet(),
            Map.Entry.comparingByValue()
        ).getKey();
        lastNumberDrawn = order.get(firstWinningBoardIndex);

        // Return final score
        return getScore(
            boardsData.boards().get(firstWinningBoardIndex),
            boardsData.numbersToDraw().subList(0, lastNumberDrawn)
        );
    }

    private int solveTwo(Day04BoardsData boardsData, Map<Integer, Integer> order) {
        int lastWinningBoardIndex;
        int lastNumberDrawn;

        // Get key with maximum value in Map
        // i.e. last winning board index which has the highest number drawn index
        lastWinningBoardIndex = Collections.max(
            order.entrySet(),
            Map.Entry.comparingByValue()
        ).getKey();
        lastNumberDrawn = order.get(lastWinningBoardIndex);

        // Return final score
        return getScore(
            boardsData.boards().get(lastWinningBoardIndex),
            boardsData.numbersToDraw().subList(0, lastNumberDrawn)
        );
    }

    public static void main(String[] args) {
        Day04 day04;
        Day04BoardsData boardsData;
        Map<Integer, Integer> winningBoardOrder;
        int bingoScoreFirstBoard;
        int bingoScoreLastBoard;

        day04 = new Day04();
        boardsData = day04.parseData();
        winningBoardOrder = day04.getWinningBoards(boardsData);

        bingoScoreFirstBoard = day04.solveOne(boardsData, winningBoardOrder);
        System.out.println("part one solution: " + bingoScoreFirstBoard);

        bingoScoreLastBoard = day04.solveTwo(boardsData, winningBoardOrder);
        System.out.println("part two solution: " + bingoScoreLastBoard);
    }
}
