"""
Script to detect markers in datastream.
"""
import os

from day import Day


class Day06(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def read_data(self) -> str:
        # Read input datastream as string
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            return file.read()

    def solve_one(self) -> int:
        # Find number of characters that need to be processed before the first start-of-packet marker is detected,
        # i.e. 4 unique characters in a row have been detected
        distinct_chars = 4
        for i in range(distinct_chars - 1, len(self.data)):
            # Check if last 4 characters in datastream are unique
            char_set = {self.data[i - j] for j in range(distinct_chars)}
            if len(char_set) == distinct_chars:
                return i + 1

    def solve_two(self) -> int:
        # Find number of characters that need to be processed before the first start-of-message marker is detected,
        # i.e. 14 unique characters in a row have been detected
        distinct_chars = 14
        for i in range(distinct_chars - 1, len(self.data)):
            # Check if last 14 characters in datastram are unique
            char_set = {self.data[i - j] for j in range(distinct_chars)}
            if len(char_set) == distinct_chars:
                return i + 1


def main():
    day06 = Day06()

    start_of_packet_marker = day06.solve_one()
    print(f"part one solution: {start_of_packet_marker}")

    start_of_message_marker = day06.solve_two()
    print(f"part two solution: {start_of_message_marker}")


if __name__ == "__main__":
    main()
