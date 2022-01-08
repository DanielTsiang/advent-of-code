"""
Script to find the most common bit in the corresponding position of all binary numbers in text file.
Bit criteria is also used to filter out binary numbers until one number remains.
"""
from day import Day
from typing import Union
import numpy as np


class BitCounter(object):
    """
    Keeps track of zeros and ones count for each index position in the binary numbers.
    """
    def __init__(self):
        self.zeros = 0
        self.ones = 0


class Day03(Day):
    def __init__(self):
        super().__init__()
        self._parsed_data = self._parse_data()

    def _parse_data(self) -> list[str]:
        # Return list of binary number strings
        return self._data

    @staticmethod
    def count_bits(binary_number_length: int, binary_numbers: Union[list[str], np.ndarray]) -> list[BitCounter]:
        # Store BitCounter objects in list
        counts = [BitCounter() for _ in range(binary_number_length)]

        # Count bits for each binary number
        for binary_number in binary_numbers:
            for index, bit in enumerate(binary_number):
                match bit:
                    case "0":
                        counts[index].zeros += 1
                    case "1":
                        counts[index].ones += 1
                    case _:
                        raise ValueError(f"'{bit}' is an invalid bit.")

        return counts

    def solve_one(self) -> int:
        # Get list for counts of each bit index
        binary_number_length = len(self._parsed_data[0])
        counts = self.count_bits(binary_number_length, self._parsed_data)

        # Gamma rate and epsilon rate are determined using most and least common bit count, respectively
        binary_gamma_rate = []
        binary_epsilon_rate = []
        for index in range(binary_number_length):
            if counts[index].zeros > counts[index].ones:
                binary_gamma_rate.append("0")
                binary_epsilon_rate.append("1")
            else:
                binary_gamma_rate.append("1")
                binary_epsilon_rate.append("0")

        # Convert binary gamma and epsilon rates (in string form) from binary numbers (base 2) to decimal numbers
        gamma_rate = int("".join(binary_gamma_rate), 2)
        epsilon_rate = int("".join(binary_epsilon_rate), 2)

        # Multiply two rates together to get power consumption
        return gamma_rate * epsilon_rate

    def solve_two(self) -> int:
        # Get length of binary numbers
        binary_number_length = len(self._parsed_data[0])

        # Find one binary number that matches bit criteria
        # Oxygen generator and CO2 scrubber ratings use most and least common value in bit position, respectively
        oxygen_data = np.array(self._parsed_data.copy())
        co2_data = np.array(self._parsed_data.copy())
        for index in range(binary_number_length):
            # Calculate bit counts for each iteration's dataset
            oxygen_counts = self.count_bits(binary_number_length, oxygen_data)
            co2_counts = self.count_bits(binary_number_length, co2_data)

            # Get most & least common bits for oxygen and co2 datasets at required index, respectively
            oxygen_most_common = "0" if oxygen_counts[index].zeros > oxygen_counts[index].ones else "1"
            co2_least_common = "1" if co2_counts[index].zeros > co2_counts[index].ones else "0"

            if len(oxygen_data) > 1:
                # Filter array to keep only binary numbers that have the most common bit at required index
                oxygen_mask = np.char.startswith(oxygen_data, oxygen_most_common, index)
                oxygen_data = oxygen_data[oxygen_mask]

            if len(co2_data) > 1:
                # Filter array to keep only binary numbers that have the least common bit at required index
                co2_mask = np.char.startswith(co2_data, co2_least_common, index)
                co2_data = co2_data[co2_mask]

            if len(oxygen_data) == len(co2_data) == 1:
                break

        oxygen_generator_rating = int(oxygen_data[0], 2)
        co2_scrubber_rating = int(co2_data[0], 2)

        # Multiply two ratings together to get life support rating
        return oxygen_generator_rating * co2_scrubber_rating


if __name__ == "__main__":
    day03 = Day03()

    power_consumption = day03.solve_one()
    print(f"part one solution: {power_consumption}")

    life_support_rating = day03.solve_two()
    print(f"part two solution: {life_support_rating}")
