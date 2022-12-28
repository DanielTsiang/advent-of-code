"""
Script to determine size of directories in a filesystem.
"""
from collections import defaultdict
from itertools import accumulate
import re

from day import Day


class Day07(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def get_dir_sizes(self, data: list[str]) -> defaultdict[str, int]:
        # Map directory paths to their sizes
        dir_sizes = defaultdict(int)
        dir_path = []
        file_regex = "(\d+) ([a-z]+.?[a-z]*)"
        cd_into_regex = "\$ cd ([a-z/]+)"
        cd_up_regex = "\$ cd .."

        for line in data:
            if file_match := re.search(file_regex, line):
                # Add file size to directory size
                size = int(file_match[1])

                # Use accumulate to create directory paths from the root path.
                # This will ensure unique directory paths, even if directories with the same name exist elsewhere,
                # so this will prevent falsely combining directory sizes with the same directory name.
                for dir in accumulate(dir_path):
                    dir_sizes[dir] += size

            elif cd_into_match := re.search(cd_into_regex, line):
                # Add directory to list of dirs, append / to end of directory name if not already there
                dir = cd_into_match[1]
                if dir[-1] != "/":
                    dir += "/"
                dir_path.append(dir)

            elif re.search(cd_up_regex, line):
                # Remove directory from list of dirs
                dir_path.pop()

        return dir_sizes

    def solve_one(self) -> int:
        # Find all the directories with a total size of at most 100000,
        # and calculate sum of the total sizes of these directories
        dir_sizes = self.get_dir_sizes(self.data)

        # Sum directory sizes with a total size of at most 100000
        return sum(size for size in dir_sizes.values() if size <= 100000)

    def solve_two(self) -> int:
        # Find the smallest directory that, if deleted, would free up enough space
        total_space = 70000000
        min_space_required = 30000000

        # Find required space to free up
        dir_sizes = self.get_dir_sizes(self.data)
        current_free_space = total_space - dir_sizes["/"]
        extra_free_space_required = min_space_required - current_free_space

        # Find the first directory that is larger than the required space
        return min(size for size in dir_sizes.values() if size >= extra_free_space_required)


def main():
    day07 = Day07()

    part_one_size = day07.solve_one()
    print(f"part one solution: {part_one_size}")

    part_two_size = day07.solve_two()
    print(f"part two solution: {part_two_size}")


if __name__ == "__main__":
    main()
