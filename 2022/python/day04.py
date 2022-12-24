"""
Script to find overlapping ranges.
"""
import os

from day import Day


class Range:
    def __init__(self, range: list[str, str]):
        self.start = int(range[0])
        self.end = int(range[1])


class Day04(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def read_data(self) -> list[tuple[Range, Range]]:
        # Read input data from text file
        data = []
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            for line in file:
                # Parse each line into a tuple of Ranges
                range_1, range_2 = line.strip().split(",")
                range_1 = range_1.split("-")
                range_2 = range_2.split("-")
                data.append((Range(range_1), Range(range_2)))

        return data

    def solve_one(self) -> int:
        # Find total count of complete overlaps, where one range is a subset of another range
        subset_count = 0
        for range_1, range_2 in self.data:
            # Check if range_1 is a subset of range_2
            if range_1.start >= range_2.start and range_1.end <= range_2.end:
                subset_count += 1
            # Check if range_2 is a subset of range_1
            elif range_2.start >= range_1.start and range_2.end <= range_1.end:
                subset_count += 1

        return subset_count

    def solve_two(self) -> int:
        # Find total count of partial overlaps, where one range overlaps another range
        return sum(
            # Check if ranges overlap
            range_1.start <= range_2.end and range_1.end >= range_2.start
            for range_1, range_2 in self.data
        )


def main():
    day04 = Day04()

    subset_count = day04.solve_one()
    print(f"part one solution: {subset_count}")

    overlap_count = day04.solve_two()
    print(f"part two solution: {overlap_count}")


if __name__ == "__main__":
    main()
