"""
Script to find the total of the top 3 sums, where each block of numbers form a sum.
"""
from day import Day


class Day01(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def solve_one(self) -> int:
        # Find the max sum, where each block of numbers form a sum
        max_value = 0
        counter = 0

        for value in self.data:
            if value != "":
                counter += int(value)
                if counter > max_value:
                    max_value = counter
            else:
                counter = 0

        return max_value

    def solve_two(self) -> int:
        # Find the total of the top 3 sums, where each block of numbers form a sum
        sums = []
        counter = 0

        for value in self.data:
            if value != "":
                counter += int(value)

            else:
                sums.append(counter)
                counter = 0

        # Sort the list in descending order
        sums.sort(reverse=True)

        # Return the sum of the top 3 values
        return sum(sums[:3])


def main():
    day01 = Day01()

    top_sum = day01.solve_one()
    print(f"part one solution: {top_sum}")

    top_3_sums = day01.solve_two()
    print(f"part two solution: {top_3_sums}")


if __name__ == "__main__":
    main()
