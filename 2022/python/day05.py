"""
Script to find final stack arrangement after shifting values between stacks.
"""
from collections import deque
from copy import deepcopy
import os
import re

from day import Day


class Instruction:
    def __init__(self, amount: str, start: str, end: str):
        self.amount: int = int(amount)
        self.start: int = int(start)
        self.end: int = int(end)


class Day05(Day):
    def __init__(self):
        super().__init__()
        self.num_stacks = 9
        self.stacks, self.instructions = self.read_data()

    def read_data(self) -> tuple[list[deque[str]], list[Instruction]]:
        # Read input data from text file
        stacks = [deque() for _ in range(self.num_stacks)]
        instructions = []

        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            regex = "move (\d+) from (\d+) to (\d+)"
            for line in file:

                # Parse columns into stack arrangement
                if "[" in line:
                    for stack, i in enumerate(range(1, (self.num_stacks * 4) - 1, 4)):
                        if i >= len(line):
                            break

                        char = line[i]
                        if char.isalpha():
                            stacks[stack].appendleft(char)

                # Parse moving instructions
                elif matches := re.search(regex, line):
                    instruction = Instruction(matches[1], matches[2], matches[3])
                    instructions.append(instruction)

        return stacks, instructions

    def solve_one(self) -> str:
        stacks = deepcopy(self.stacks)
        for instruction in self.instructions:
            # Move value from stacks one at a time per instruction
            for _ in range(instruction.amount):
                value = stacks[instruction.start - 1].pop()
                stacks[instruction.end - 1].append(value)

        # Find values at the top of each stack
        top_stacks = [stack[-1] for stack in stacks]
        return ''.join(top_stacks)

    def solve_two(self) -> str:
        stacks = deepcopy(self.stacks)
        for instruction in self.instructions:
            # Move multiple values from stacks at a time per instruction, maintaining order
            moving_stack = []
            for _ in range(instruction.amount):
                value = stacks[instruction.start - 1].pop()
                moving_stack.append(value)
            for _ in range(instruction.amount):
                value = moving_stack.pop()
                stacks[instruction.end - 1].append(value)

        # Find values at the top of each stack
        top_stacks = [stack[-1] for stack in stacks]
        return ''.join(top_stacks)


def main():
    day05 = Day05()

    # Top stacks from shifting single value per move
    top_stacks_single_move = day05.solve_one()
    print(f"part one solution: {top_stacks_single_move}")

    # Top stacks from shifting multiple values per move
    top_stacks_multiple_move = day05.solve_two()
    print(f"part two solution: {top_stacks_multiple_move}")


if __name__ == "__main__":
    main()
