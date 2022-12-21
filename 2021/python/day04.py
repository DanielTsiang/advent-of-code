"""
Script to find the first and last winning Bingo scores from multiple boards.
"""
from day import Day
import numpy as np


class Board(object):
    """
    Each square board is a 2D NumPy array contain numbers in string form.
    """
    def __init__(self, rows: np.ndarray):
        self.rows = rows


class BoardsData(object):
    """
    Keeps track of numbers to draw, and also the rows and columns data as a list of boards.
    """
    def __init__(self, numbers_to_draw: list[int], boards: list[Board]):
        self.numbers_to_draw = numbers_to_draw
        self.boards = boards


class Day04(Day):
    def __init__(self):
        super().__init__()
        self._parsed_data = self._parse_data()
        self._length = len(self._parsed_data.boards[0].rows)

    def _parse_data(self) -> BoardsData:
        # Slice input data into numbers drawn and boards data
        numbers_to_draw = [
            int(number)
            for number in self._data[0].split(",")
        ]
        boards = self._data[2:]

        # Parse boards data into 2D NumPy array containing array of numbers,
        # filtering out empty spaces between boards.
        parsed_boards = np.array(
            [
                [
                    int(number)
                    for number in board_row.split()
                ]
                for board_row in boards
                if board_row.split()
            ]
        )

        # Calculate number of square boards in dataset
        num_boards = parsed_boards.shape[0] // parsed_boards.shape[1]

        # Split boards into separate 2D boards
        split_boards = [Board(board) for board in np.split(parsed_boards, num_boards)]

        return BoardsData(numbers_to_draw, split_boards)

    def _check_board(self, board: Board, numbers_drawn: list[int]) -> bool:
        for i in range(self._length):
            # Check row
            if all(board.rows[i][j] in numbers_drawn for j in range(self._length)):
                return True

            # Check column
            if all(board.rows[j][i] in numbers_drawn for j in range(self._length)):
                return True

        # Not a winning Board
        return False

    def get_winning_boards(self) -> dict:
        # Initialize dict to store mapping of winning Board indices to last number drawn indices
        order = {}

        # Loop through numbers to draw, starting from minimum length to win
        for i in range(self._length, len(self._parsed_data.numbers_to_draw)):
            # Loop through Boards, keeping track of index
            for j, board in enumerate(self._parsed_data.boards):
                # Check if already seen winning Board before
                if j in order:
                    continue

                # Check if winning Board
                if self._check_board(board, self._parsed_data.numbers_to_draw[:i]):
                    # Add winning Board index and last number drawn index to `order` dict
                    order[j] = i

        # Return mapping of winning Board indices to last number drawn indices
        return order

    def _get_score(self, board: Board, numbers_drawn: list[int]) -> int:
        # Sum numbers in winning board that did not match numbers drawn
        unmarked_sum = 0
        for i in range(self._length):
            for j in range(self._length):
                if board.rows[i][j] not in numbers_drawn:
                    unmarked_sum += board.rows[i][j]

        # Return final score
        return unmarked_sum * numbers_drawn[-1]

    def solve_one(self, order: dict) -> int:
        # Get key with minimum value in dictionary
        # i.e. first winning Board index which has the lowest number drawn index
        first_winning_board_index = min(order, key=order.get)
        last_number_drawn = order[first_winning_board_index]

        # Return final score
        return self._get_score(
            self._parsed_data.boards[first_winning_board_index],
            self._parsed_data.numbers_to_draw[:last_number_drawn]
        )

    def solve_two(self, order: dict) -> int:
        # Get key with maximum value in dictionary
        # i.e. last winning Board index which has the highest number drawn index
        last_winning_board_index = max(order, key=order.get)
        last_number_drawn = order[last_winning_board_index]

        # Return final score
        return self._get_score(
            self._parsed_data.boards[last_winning_board_index],
            self._parsed_data.numbers_to_draw[:last_number_drawn]
        )


if __name__ == "__main__":
    day04 = Day04()

    winning_board_order = day04.get_winning_boards()

    bingo_score_first_board = day04.solve_one(winning_board_order)
    print(f"part one solution: {bingo_score_first_board}")

    bingo_score_last_board = day04.solve_two(winning_board_order)
    print(f"part two solution: {bingo_score_last_board}")
