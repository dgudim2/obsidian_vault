package com.kloud;

import com.kloud.model.Cinema;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        long time = System.currentTimeMillis();

        List<Cinema> cinemas = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(new File("data.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!data.isEmpty()) {
                    cinemas.add(Cinema.fromStr(data));
                }
            }
            myReader.close();
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("\n\nSorted by title from Z to A: ");
        System.out.println("-----------------------------");
        cinemas.sort((o1, o2) -> o2.title.compareTo(o1.title));
        printCinemas(cinemas);

        System.out.println("\n\nSorted by rating (highest to lowest): ");
        System.out.println("-----------------------------");
        cinemas.sort((o1, o2) -> Float.compare(o2.rating, o1.rating));
        printCinemas(cinemas);

        System.out.println("\n\nSorted by price (lowest to highest): ");
        System.out.println("-----------------------------");
        cinemas.sort((o1, o2) -> Float.compare(o1.pricePerPerson, o2.pricePerPerson));
        printCinemas(cinemas);

        System.out.println("\n\n-----------------------------");

        StringBuilder prettyPrint = new StringBuilder();
        for(var cinema: cinemas){
            prettyPrint.append(cinema.title).append("/").append(cinema.rating).append("\n").append(cinema.getSeancesStr()).append("\n");
        }

        try {
            File outFile = new File("prettyprint.txt");
            if (outFile.createNewFile()) {
                System.out.println("File created: " + outFile.getName());
            }
            FileWriter writer = new FileWriter(outFile);
            writer.write(prettyPrint.toString());
            System.out.println("Content written to " + outFile.getName());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nCave Johnson, we're done here | took " + (System.currentTimeMillis() - time) + "ms");
    }

    private static void printCinemas(@NotNull List<Cinema> cinemas) {
        for(var cinema: cinemas){
            System.out.println(cinema);
        }
    }

}