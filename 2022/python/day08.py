"""
Script to determine number of trees not hidden by other trees and their scenic scores.
"""
import os

from day import Day


class Day08(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()
        self.rows = len(self.data)
        self.columns = len(self.data[0])

    def read_data(self) -> list[list[int]]:
        data = []
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            for line in file:
                row = [int(digit) for digit in line.strip()]
                data.append(row)
        return data

    def solve_one(self) -> int:
        # Find number of visible trees, i.e. trees that are the taller than any other tree in any 4 directions
        total_visible_trees = 0

        # Find external visible trees
        external_visible_trees = 2 * self.rows + 2 * self.columns - 4
        total_visible_trees += external_visible_trees

        # Find internal visible trees
        for row in range(1, self.rows - 1):
            for column in range(1, self.columns - 1):
                # Calculate heights
                height = self.data[row][column]
                max_height_right = max(self.data[row][column + 1:])
                max_height_left = max(self.data[row][:column])
                max_height_up = max(self.data[i][column] for i in range(row))
                max_height_down = max(self.data[i][column] for i in range(row + 1, self.rows))

                # Check if tree is visible in any 4 directions
                if height > max_height_right \
                        or height > max_height_left \
                        or height > max_height_up \
                        or height > max_height_down:
                    total_visible_trees += 1

        return total_visible_trees

    @staticmethod
    def compute_viewing_distance(current_tree: int, trees: list[int]) -> int:
        viewing_distance = 0
        for tree in trees:
            viewing_distance += 1
            if tree >= current_tree:
                break

        return viewing_distance

    def solve_two(self) -> int:
        # Find the tree with the highest scenic score
        max_scenic_score = 0

        # Find scenic score for each tree, i.e. its viewing distance multiplied together in each of the 4 directions
        for row in range(self.rows):
            for column in range(self.columns):
                # Calculate height of tree
                height = self.data[row][column]

                # Calculate viewing distance in each direction,
                # i.e. until a tree of equal or greater height is found inclusive
                viewing_distance_right = self.compute_viewing_distance(height, self.data[row][column + 1:])
                viewing_distance_down = self.compute_viewing_distance(height, [self.data[i][column] for i in
                                                                               range(row + 1, self.rows)])

                # Reverse order to check towards the left and upwards from the current tree
                viewing_distance_left = self.compute_viewing_distance(height, self.data[row][:column][::-1])
                viewing_distance_up = self.compute_viewing_distance(height,
                                                                    [self.data[i][column] for i in range(row)][::-1])

                # Calculate and set scenic score if it is the highest so far
                scenic_score = viewing_distance_right * viewing_distance_left * viewing_distance_up * viewing_distance_down
                max_scenic_score = max(max_scenic_score, scenic_score)

        return max_scenic_score


def main():
    day08 = Day08()

    total_visible_trees = day08.solve_one()
    print(f"part one solution: {total_visible_trees}")

    max_scenic_score = day08.solve_two()
    print(f"part two solution: {max_scenic_score}")


if __name__ == "__main__":
    main()
