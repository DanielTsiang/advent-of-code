"""
Script to determine number of trees not hidden by other trees and their scenic scores.
"""
import os

import numpy as np
import pandas as pd

from day import Day


class Day08(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()
        self.rows = len(self.data)
        self.columns = len(self.data[0])

    def read_data(self) -> pd.DataFrame:
        data = []
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            for line in file:
                row = [int(digit) for digit in line.strip()]
                data.append(row)
        return pd.DataFrame(data)

    def solve_one(self) -> int:
        # Find number of visible trees, i.e. trees that are the higher in any 4 directions
        total_visible_trees = 0

        # Find external visible trees
        external_visible_trees = 2 * self.rows + 2 * self.columns - 4
        total_visible_trees += external_visible_trees

        # Find internal visible trees
        for row in range(1, self.rows - 1):
            for column in range(1, self.columns - 1):
                # Calculate heights
                height = self.data.iloc[row, column]
                max_height_right = self.data.iloc[row, column + 1:].max()
                max_height_left = self.data.iloc[row, :column].max()
                max_height_up = self.data.iloc[:row, column].max()
                max_height_down = self.data.iloc[row + 1:, column].max()

                # Check if tree is visible in any 4 directions
                if height > max_height_right \
                        or height > max_height_left \
                        or height > max_height_up \
                        or height > max_height_down:
                    total_visible_trees += 1

        return total_visible_trees

    def solve_two(self) -> int:
        # Find the tree with the highest scenic score
        max_scenic_score = 0

        # Find scenic score for each tree, i.e. its viewing distance multiplied together in each of the 4 directions
        for row in range(self.rows):
            for column in range(self.columns):
                # Calculate height of tree
                height = self.data.iloc[row, column]

                # Calculate viewing distance in each direction,
                # i.e. until a tree of equal or greater height is found inclusive
                # Find viewing distance to the right
                viewing_distance_right = 0
                for i in self.data.iloc[row, column + 1:]:
                    viewing_distance_right += 1
                    if i >= height:
                        break

                # Find viewing distance to the left
                viewing_distance_left = 0
                # Reverse order to check towards the left
                for i in self.data.iloc[row, :column].iloc[::-1]:
                    viewing_distance_left += 1
                    if i >= height:
                        break

                # Find viewing distance up
                viewing_distance_up = 0
                # Reverse order to check upwards
                for i in self.data.iloc[:row, column].iloc[::-1]:
                    viewing_distance_up += 1
                    if i >= height:
                        break

                # Find viewing distance down
                viewing_distance_down = 0
                for i in self.data.iloc[row + 1:, column]:
                    viewing_distance_down += 1
                    if i >= height:
                        break

                # Calculate and set scenic score if it is the highest so far
                scenic_score = viewing_distance_right * viewing_distance_left * viewing_distance_up * viewing_distance_down
                if scenic_score > max_scenic_score:
                    max_scenic_score = scenic_score

        return max_scenic_score


def main():
    day08 = Day08()

    part_one_trees = day08.solve_one()
    print(f"part one solution: {part_one_trees}")

    part_two_trees = day08.solve_two()
    print(f"part two solution: {part_two_trees}")


if __name__ == "__main__":
    main()
