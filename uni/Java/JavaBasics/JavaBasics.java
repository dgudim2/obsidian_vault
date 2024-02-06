import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Base starting class, hosts all the tasks
 *
 * @version 1.0
 * @author: Danila Gudim / ITfuc-22 / 20222136
 */
public class JavaBasics {

    // Constants for colors
    // See: https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797
    private static final String COL_GREEN = "\033[1;32m";
    private static final String COL_YELLOW = "\033[1;33m";
    private static final String COL_RST = "\033[0m";

    private static final Random RAND = new Random();

    /**
     * Finds the number of vowels given a string
     *
     * @param str The string to find vowels in
     * @return The number of vowels
     */
    protected static int numVowels(String str) {
        int vowels = 0;

        for (int i = 0; i < str.length(); i++) {
            var charr = Character.toLowerCase(str.charAt(i));
            if (charr == 'a' || charr == 'e' || charr == 'i' || charr == 'o' || charr == 'u') {
                vowels++;
            }
        }
        return vowels;
    }

    /**
     * Generates a random integer from {@code min} to {@code max} (inclusive)
     *
     * @param min Lower bound
     * @param max Upper bound
     * @return The generated number
     */
    protected static int randomRange(int min, int max) {
        return RAND.nextInt(max - min) + min;
    }

    /**
     * Converts an array to a string with a specified gap
     *
     * @param arr Array to convert
     * @param gap Desired gap between numbers
     * @return String representation of the array
     */
    protected static String arrToString(int[] arr, int gap) {
        String str = "[";
        for (int i : arr) {
            str += String.format("%" + gap + "s", i);
        }
        return str + "]";
    }

    /**
     * Entrypoint to the program
     * @param args Passed arguments
     */
    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        System.out.print("Input your name: ");
        String name = scanner.next();
        System.out.print("Input your surname: ");
        String surname = scanner.next();
        scanner.close();

        System.out.println();

        var rows = name.length();
        var cols = surname.length();

        // Task 3
        // Self-explanatory, create a 2d array
        var arr = new int[rows][cols];
        var rowSums = new float[rows];
        var colSums = new float[cols];

        var maxValue = Integer.MIN_VALUE;
        var minAvgColIndex = Integer.MAX_VALUE;
        var minAvgColMinValue = Integer.MAX_VALUE;

        int minRandom = numVowels(name);
        int maxRandom = rows + cols;

        // Start of task 4, part of task 5 and 7
        // Fill the array with random values, find sums for columns and rows and find the max value
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                arr[x][y] = randomRange(minRandom, maxRandom);
                rowSums[x] += arr[x][y];
                colSums[y] += arr[x][y];
                if (x != minRandom && y != minRandom) {
                    maxValue = Math.max(maxValue, arr[x][y]);
                }
            }
        }

        int maxRandomLen = (int) (Math.log10(maxRandom) + 1);
        int maxRowNumberLen = (int) (Math.log10(rows) + 1);
        int maxColNumberLen = (int) (Math.log10(cols) + 1);
        int targetGap = Math.max(maxColNumberLen, maxRandomLen + 3) + 5;

        // Continuation of task 4, 5, 6, 7, 8
        // Print everything in a nice table
        System.out.println(COL_GREEN + "2D Array with some info: " + COL_RST);
        System.out.printf("%" + (maxRowNumberLen + 7) + "s", "");

        // Print header
        for (int y = 0; y < cols; y++) {
            System.out.printf("%" + targetGap + "s", "col " + y);
        }
        System.out.println();
        // Print the body of the table including row averages and sorted rows
        for (int x = 0; x < rows; x++) {
            var numValuesOverAverage = 0;
            var rowAverage = rowSums[x] / (float) rows;
            var sorted = arr[x].clone();
            Arrays.sort(sorted);
            for (int y = 0; y < cols; y++) {
                if (arr[x][y] > rowAverage) {
                    numValuesOverAverage++;
                }
            }
            System.out.printf("row %" + maxRowNumberLen + "s: %s avg: %.4f | values > avg: %s | sorted: %s\n",
                    x, arrToString(arr[x], targetGap), rowAverage, numValuesOverAverage, arrToString(sorted, maxRandomLen + 1));
        }

        // Print column averages (Part of Task 5) and find index of column with the minimum average (Part of Task 9)
        System.out.printf("%" + (maxRowNumberLen + 6) + "s ", "avg: ");
        for (int y = 0; y < cols; y++) {
            var avg = colSums[y] / (float) cols;
            if (avg < minAvgColIndex) {
                minAvgColIndex = y;
            }
            System.out.printf("%" + targetGap + "s", String.format("%.4f", avg));
        }
        // Find min value in the column with the minimum average value
        for (int x = 0; x < rows; x++) {
            var colValue = arr[x][minAvgColIndex];
            if (colValue < minAvgColMinValue) {
                minAvgColMinValue = colValue;
            }
        }

        System.out.println("\n\n" + COL_GREEN + "Max value of the 2D Array excluding " + COL_YELLOW + "column and row " + minRandom + COL_GREEN + ": " + maxValue + COL_RST);
        System.out.println(COL_GREEN + "Min value from the " + COL_YELLOW + minAvgColIndex + "'th" + COL_GREEN + " column: " + COL_YELLOW + minAvgColMinValue + COL_RST);
    }
}
