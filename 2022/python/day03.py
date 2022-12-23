"""
Script to find values of common items.
"""
from string import ascii_lowercase, ascii_uppercase

from day import Day

lowercase_map = {
            letter: ascii_lowercase.index(letter) + 1
            for letter in ascii_lowercase
        }

uppercase_map = {
    letter: ascii_uppercase.index(letter) + 27
    for letter in ascii_uppercase
}


class Day03(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def solve_one(self) -> int:
        # Find the total value of common items, i.e. letters which appear in both halves of the string
        total_value = 0
        for line in self.data:
            midpoint = len(line) // 2
            first_half = set(line[:midpoint])
            second_half = set(line[midpoint:])

            # Find common letter between the two sets
            letter = first_half.intersection(second_half).pop()
            if letter.islower():
                total_value += lowercase_map[letter]
            else:
                total_value += uppercase_map[letter]

        return total_value

    def solve_two(self) -> int:
        # Find the total value of common items, i.e. letters which appear in each group of 3 strings
        total_value = 0
        for i in range(0, len(self.data), 3):
            string_1 = set(self.data[i])
            string_2 = set(self.data[i + 1])
            string_3 = set(self.data[i + 2])

            # Find common letter between the three sets
            letter = string_1.intersection(string_2).intersection(string_3).pop()
            if letter.islower():
                total_value += lowercase_map[letter]
            else:
                total_value += uppercase_map[letter]

        return total_value


def main():
    day03 = Day03()

    part_one_value = day03.solve_one()
    print(f"part one solution: {part_one_value}")

    part_two_value = day03.solve_two()
    print(f"part two solution: {part_two_value}")


if __name__ == "__main__":
    main()
