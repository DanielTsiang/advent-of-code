/**
 * Class for preparing Scanner which will read data from text file.
 */

package utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class Day {
    protected Scanner scanner;

    protected Day() {
        this.scanner = this.getScanner();
    }

    private Scanner getScanner() {
        Scanner scanner = null;
        String classPath;
        String fileSeparator;
        String dataDir;
        String directoryPath;
        String className;
        String fileName;

        // Get directory path & data filename
        classPath = System.getProperty("java.class.path");
        fileSeparator = File.separator;
        dataDir = "data".concat(fileSeparator);
        directoryPath = classPath.split("java")[0].concat(dataDir);
        className = this.getClass().getSimpleName().toLowerCase();
        fileName = className.concat(".txt");

        // Open input data from text file into Scanner
        try {
            scanner = new Scanner(new File(directoryPath.concat(fileName)));
        } catch (IOException error) {
            System.out.println("Unable to open file.");
            error.printStackTrace();
            System.exit(0);
        }

        return scanner;
    }

}
