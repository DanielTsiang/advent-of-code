"""
Script to calculate the submarine horizontal position, depth and optionally aim after following a planned course.
"""
from day import Day


class Instruction(object):
    """
    Stores direction & distance instructions.
    """
    def __init__(self, direction: str, magnitude: int):
        self.direction = direction
        self.magnitude = magnitude


class Coordinate(object):
    """
    Stores position, depth and optionally aim coordinates.
    """
    def __init__(self, position: int, depth: int, aim: int = 0):
        self.position = position
        self.depth = depth
        self.aim = aim


class Day02(Day):
    def __init__(self):
        super().__init__()
        self._parsed_data = self._parse_data()

    def _parse_data(self) -> list[Instruction]:
        # Split each line into direction and magnitude, then append to instructions list as Instruction objects
        instructions = []
        for line in self._data:
            split_line = line.split()
            direction = split_line[0]
            distance = int(split_line[1])
            instructions.append(Instruction(direction, distance))

        return instructions

    def solve_one(self) -> int:
        # Calculate horizontal position and depth after following the planned course
        instructions = self._parsed_data
        coordinate = Coordinate(0, 0)

        for instruction in instructions:
            direction = instruction.direction
            magnitude = instruction.magnitude

            match direction:
                case "forward":
                    coordinate.position += magnitude
                case "down":
                    coordinate.depth += magnitude
                case "up":
                    coordinate.depth -= magnitude
                case _:
                    raise ValueError(f"Direction '{direction}' not recognized.")

        # Multiply final horizontal position by final depth
        return coordinate.position * coordinate.depth

    def solve_two(self) -> int:
        # Calculate horizontal position, depth and aim after following the planned course
        instructions = self._parsed_data
        coordinate = Coordinate(0, 0, 0)

        for instruction in instructions:
            direction = instruction.direction
            magnitude = instruction.magnitude

            match direction:
                case "forward":
                    coordinate.position += magnitude
                    coordinate.depth += (coordinate.aim * magnitude)
                case "down":
                    coordinate.aim += magnitude
                case "up":
                    coordinate.aim -= magnitude
                case _:
                    raise ValueError(f"Direction '{direction}' not recognized.")

        # Multiply final horizontal position by final depth
        return coordinate.position * coordinate.depth


def main():
    day02 = Day02()

    coordinates_one = day02.solve_one()
    print(f"part one solution: {coordinates_one}")

    coordinates_two = day02.solve_two()
    print(f"part two solution: {coordinates_two}")


if __name__ == "__main__":
    main()
