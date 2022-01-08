"""
Script to count the number of times a depth measurement increases from the previous measurement,
when considering one-measurement & three-measurement sliding windows.
"""
from day import Day


class Day01(Day):
    def __init__(self):
        super().__init__()
        self._parsed_data = self._parse_data()

    def _parse_data(self) -> list:
        return self._data

    def solve_one(self) -> int:
        # Sum up number of times a depth measurement is higher than previous
        data = self._parsed_data
        return sum(data[i] > data[i - 1] for i in range(1, len(data)))

    def solve_two(self) -> int:
        # Make use of the fact that for list [a, b, c, d], (b + c + d) is greater than (a + b + c) if d > a
        data = self._parsed_data
        return sum(data[i] > data[i - 3] for i in range(3, len(data)))


def main():
    day01 = Day01()

    counter_one = day01.solve_one()
    print(f"part one solution: {counter_one}")

    counter_two = day01.solve_two()
    print(f"part two solution: {counter_two}")


if __name__ == "__main__":
    main()
