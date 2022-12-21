"""
Class for reading data from text file.
"""
from pathlib import Path
import os


class Day(object):
    def __init__(self):
        self._directory_path = os.path.join(Path(__file__).parents[1].resolve(), "data")
        self._data_filename = f"{self.__class__.__name__.lower()}.txt"
        self._data = self._read_data()

    def _read_data(self) -> list[str]:
        # Read input data from text file
        with open(os.path.join(self._directory_path, self._data_filename), "r") as file:
            return file.read().splitlines()
