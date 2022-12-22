"""
Script to find the total score for the two-letter combinations.
"""
from day import Day


class Day02(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def solve_one(self) -> int:
        # Find the total score for each row of two-letter combinations
        play_map = {
            "A X": 4,
            "A Y": 8,
            "A Z": 3,
            "B X": 1,
            "B Y": 5,
            "B Z": 9,
            "C X": 7,
            "C Y": 2,
            "C Z": 6,
        }

        return sum(play_map[value] for value in self.data)

    def solve_two(self) -> int:
        # Find the total score for each row of two-letter combinations
        result_map = {
            "A X": 3,
            "A Y": 4,
            "A Z": 8,
            "B X": 1,
            "B Y": 5,
            "B Z": 9,
            "C X": 2,
            "C Y": 6,
            "C Z": 7,
        }

        return sum(result_map[value] for value in self.data)


def main():
    day02 = Day02()

    part_one_score = day02.solve_one()
    print(f"part one solution: {part_one_score}")

    part_two_score = day02.solve_two()
    print(f"part two solution: {part_two_score}")


if __name__ == "__main__":
    main()
