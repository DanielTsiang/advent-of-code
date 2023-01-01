"""
Script to determine positions visited by the tail end of a rope being pulled by the head end.
"""
import os

from day import Day


class Coordinate:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __repr__(self):
        return f"({self.x}, {self.y})"

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return hash((self.x, self.y))

    def __add__(self, other):
        return Coordinate(self.x + other.x, self.y + other.y)

    def __sub__(self, other):
        return Coordinate(self.x - other.x, self.y - other.y)


class Instruction:
    def __init__(self, direction: str, distance: str):
        self.direction: str = direction
        self.distance: int = int(distance)


class Movement:
    def __init__(self):
        pass

    RIGHT = Coordinate(1, 0)
    LEFT = Coordinate(-1, 0)
    UP = Coordinate(0, 1)
    DOWN = Coordinate(0, -1)


def is_touching(knot1: Coordinate, knot2: Coordinate) -> bool:
    # Check if 2 knots are touching
    x_distance = abs(knot1.x - knot2.x)
    y_distance = abs(knot1.y - knot2.y)
    return x_distance <= 1 and y_distance <= 1


def get_tail_movement(distance: int) -> int:
    # Get direction of movement of the tail knot in x or y direction
    if distance == 0:
        return 0
    elif distance > 0:
        return 1
    else:
        return -1


def get_new_tail_position(tail_position: Coordinate, head_position: Coordinate) -> Coordinate:
    if is_touching(tail_position, head_position):
        return tail_position

    # Calculate direction of movement of the tail knot
    direction = head_position - tail_position

    # Calculate new tail position
    x_distance = get_tail_movement(direction.x)
    y_distance = get_tail_movement(direction.y)
    return tail_position + Coordinate(x_distance, y_distance)


def get_new_head_position(head_position: Coordinate, instruction: Instruction) -> Coordinate:
    match instruction.direction:
        case "R":
            return head_position + Movement.RIGHT
        case "L":
            return head_position + Movement.LEFT
        case "U":
            return head_position + Movement.UP
        case "D":
            return head_position + Movement.DOWN
        case _:
            raise ValueError(f"Unknown direction: {instruction.direction}")


class Day09(Day):
    def __init__(self):
        super().__init__()
        self.data = self.read_data()

    def read_data(self) -> list[Instruction]:
        data = []
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            for line in file:
                row = line.split(" ")
                data.append(Instruction(row[0], row[1]))
        return data

    def calculate_tail_positions_visited(self, num_knots: int) -> int:
        # Determine number of positions visited by the tail end of a rope being pulled by the head end,
        # with all knots starting at the origin
        knot_positions = [Coordinate(0, 0) for _ in range(num_knots)]
        tail_positions_visited = {Coordinate(0, 0)}

        for instruction in self.data:
            for _ in range(instruction.distance):
                # Determine new head position one step at a time
                knot_positions[-1] = get_new_head_position(knot_positions[-1], instruction)
                for i in range(len(knot_positions) - 1, 0, -1):
                    # Determine new knot positions after the head one step at a time.
                    # Adjacent knots must always be touching.
                    knot_positions[i - 1] = get_new_tail_position(knot_positions[i - 1], knot_positions[i])
                # Update the positions visited by the tail knot set
                tail_positions_visited.add(knot_positions[0])

        return len(tail_positions_visited)

    def solve_one(self) -> int:
        # Determine number of positions visited by the tail end of a rope being pulled by the head end,
        # with both ends starting at the origin, i.e. 2 knots
        num_knots = 2
        return self.calculate_tail_positions_visited(num_knots)

    def solve_two(self) -> int:
        # Determine number of positions visited by the tail end of a rope being pulled by the head end,
        # with all 10 knots starting at the origin
        num_knots = 10
        return self.calculate_tail_positions_visited(num_knots)


def main():
    day09 = Day09()

    part_one_positions_visited = day09.solve_one()
    print(f"part one solution: {part_one_positions_visited}")

    part_two_positions_visited = day09.solve_two()
    print(f"part two solution: {part_two_positions_visited}")


if __name__ == "__main__":
    main()
