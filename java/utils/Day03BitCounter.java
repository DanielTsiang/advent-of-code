package utils;

// Class to keep track of zeros and ones count for each index position in the binary numbers.
public class Day03BitCounter {
    private int zeros;
    private int ones;

    public Day03BitCounter() {
        this.zeros = 0;
        this.ones = 0;
    }

    public int getZeros() {
        return zeros;
    }

    public int getOnes() {
        return ones;
    }

    public void setZeros(int zeros) {
        this.zeros = zeros;
    }

    public void setOnes(int ones) {
        this.ones = ones;
    }
}
