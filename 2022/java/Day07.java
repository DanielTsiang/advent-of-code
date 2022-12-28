/**
 * Script to determine size of directories in a filesystem.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Day;


public class Day07 extends Day {

    private List<String> parseData() {
        String string;
        List<String> strings;

        // Read each line of input data as String, and store inside List
        strings = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            string = this.scanner.nextLine();
            strings.add(string);
        }

        return strings;
    }

    private Map<String, Integer> getDirSizes(List<String> strings) {
        // Map directory paths to their sizes
        Map<String, Integer> dirSizes;
        List<String> dirPath;
        Pattern filePattern;
        Matcher fileMatcher;
        Pattern cdIntoPattern;
        Matcher cdIntoMatcher;
        Pattern cdUpPattern;
        Matcher cdUpMatcher;
        String dirName;
        int size;
        StringBuilder stringBuilder;
        String fullDirPath;

        dirSizes = new HashMap<>();
        dirPath = new ArrayList<>();

        filePattern = Pattern.compile("(\\d+) ([a-z]+.?[a-z]*)");
        cdIntoPattern = Pattern.compile("\\$ cd ([a-z/]+)");
        cdUpPattern = Pattern.compile("\\$ cd ..");

        for (String line : strings) {
            fileMatcher = filePattern.matcher(line);
            cdIntoMatcher = cdIntoPattern.matcher(line);
            cdUpMatcher = cdUpPattern.matcher(line);

            // Check if line is file information
            if (fileMatcher.find()) {
                // Add file size to directory size
                size = Integer.parseInt(fileMatcher.group(1));

                // Create directory paths from the root path.
                // This will ensure unique directory paths, even if directories with the same name exist elsewhere,
                // so this will prevent falsely combining directory sizes with the same directory name.
                stringBuilder = new StringBuilder();
                for (String dir : dirPath) {
                    stringBuilder.append(dir);
                    fullDirPath = stringBuilder.toString();
                    if (!dirSizes.containsKey(fullDirPath)) {
                        dirSizes.put(fullDirPath, size);
                    } else {
                        dirSizes.put(fullDirPath, dirSizes.get(fullDirPath) + size);
                    }

                }
            }

            // Check if line is changing into directory
            else if (cdIntoMatcher.find()) {
                // Add directory to list of directories, append / to end of directory name if not already there
                dirName = cdIntoMatcher.group(1);
                if (!dirName.endsWith("/")) {
                    dirName += "/";
                }
                dirPath.add(dirName);
            }

            // Check if line is changing up a directory
            else if (cdUpMatcher.find()) {
                // Remove last directory from list of directories
                dirPath.remove(dirPath.size() - 1);
            }
        }

        return dirSizes;
    }

    private int solveOne(List<String> strings) {
        int totalSize;
        Map<String, Integer> dirSizes;

        // Find all the directories with a total size of at most 100000,
        // and calculate sum of the total sizes of these directories
        totalSize = 0;
        dirSizes = getDirSizes(strings);
        for (Integer size : dirSizes.values()) {
            if (size <= 100000) {
                totalSize += size;
            }
        }
        return totalSize;
    }

    private int solveTwo(List<String> strings) {
        // Find the smallest directory that, if deleted, would free up enough space
        int totalSpace = 70000000;
        int minSpaceRequired = 30000000;
        int currentFreeSpace;
        int extraFreeSpaceRequired;
        Map<String, Integer> dirSizes;
        int minDirSize;
        int size;

        // Find required space to free up
        dirSizes = getDirSizes(strings);
        currentFreeSpace = totalSpace - dirSizes.get("/");
        extraFreeSpaceRequired = minSpaceRequired - currentFreeSpace;

        // Find the minimum directory size that is larger than the required space
        minDirSize = Integer.MAX_VALUE;
        for (String dir : dirSizes.keySet()) {
            size = dirSizes.get(dir);
            if (size >= extraFreeSpaceRequired) {
                minDirSize = Math.min(minDirSize, size);
            }
        }
        return minDirSize;
    }

    public static void main(String[] args) {
        Day07 day07;
        List<String> strings;
        int partOneSize;
        int partTwoSize;

        day07 = new Day07();
        strings = day07.parseData();

        partOneSize = day07.solveOne(strings);
        System.out.println("part one solution: " + partOneSize);

        partTwoSize = day07.solveTwo(strings);
        System.out.println("part two solution: " + partTwoSize);
    }
}
