package utils;

import java.util.List;

// Record to keep track of numbers to draw, and also the rows and columns data
// as a List of Day04Board.
public record Day04BoardsData(
    List<Integer> numbersToDraw,
    List<Day04Board> boards
) {}
