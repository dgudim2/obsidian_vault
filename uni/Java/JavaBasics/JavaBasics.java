import java.util.Random;
import java.util.Scanner;

public class JavaBasics {

    /*
     * Name: Danila
     * Surname: Gudim
     * Group: ITfuc-22
     * Number: 20222136
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

    protected static int randomRange(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    protected static String arrToString(int[] arr, int gap) {
        String str = "[";
        for(int i = 0; i < arr.length; i++) {
            str += String.format("%" + gap + "s", arr[i]);
        }
        return str + "]";
    }

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

        var arr = new int[rows][cols];

        int minRandom = numVowels(name);
        int maxRandom = rows + cols;

        System.out.print("         ");
        for (int y = 0; y < cols; y++) {
            System.out.print("col " + y + " ");
        }
        System.out.println();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                arr[x][y] = randomRange(minRandom, maxRandom);
            }
            System.out.println("row " + x + ": " + arrToString(arr[x], 6));
        }

        var rowAverages = new float[rows];
        var colAverages = new float[cols];

        for (int x = 0; x < rows; x++) {
            int sum = 0;
            for (int y = 0; y < cols; y++) {
                sum += arr[x][y];
            }
            rowAverages[x] = sum / (float)rows;
        }

        for (int y = 0; y < cols; y++) {
            int sum = 0;
            for (int x = 0; x < rows; x++) {
                sum += arr[x][y];
            }
            colAverages[y] = sum / (float)cols;
        }

        System.out.print("\nRow averages: ");
        for (int x = 0; x < rows; x++) {
            System.out.print(rowAverages[x] + " ");
        }
        System.out.print("\nColumn averages: ");
        for (int y = 0; y < cols; y++) {
            System.out.print(colAverages[y] + " ");
        }
        System.out.println();
    }
}
