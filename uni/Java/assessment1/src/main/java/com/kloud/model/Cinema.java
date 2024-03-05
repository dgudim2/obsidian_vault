package com.kloud.model;

import com.kloud.model.enums.Genre;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cinema {
    public final String title;
    public final Genre genre;
    protected List<LocalDateTime> seances;
    public final float pricePerPerson;

    public final float rating;

    private static final Pattern extactionPattern = Pattern.compile("(.*) \\((.*?)\\*(\\d*.*?)\\*([0-9.]*)\\): (.*)");
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Cinema(String title, Genre genre, List<LocalDateTime> seances, float pricePerPerson, float rating) {
        this.title = title;
        this.genre = genre;
        this.seances = seances;
        this.pricePerPerson = pricePerPerson;
        this.rating = rating;
    }


    public String getSeancesStr() {
        StringBuilder str = new StringBuilder();
        for(var seance: seances) {
            str.append(",").append(seance.format(dateFormat));
        }
        return str.substring(1);
    }

    public static Cinema fromStr(@NotNull String s) throws IllegalArgumentException {

        System.out.println("Trying to parse '" + s + "' into a cinema object");

        Matcher m = extactionPattern.matcher(s);
        if (m.groupCount() == 5 && m.find()) {
            String name = m.group(1);
            Genre genre = Genre.valueOf(m.group(2).toUpperCase());
            float price = Float.parseFloat(m.group(3).replace("eur", ""));
            float rating = Float.parseFloat(m.group(4));
            String[] seances = m.group(5).split("\\\\");
            List<LocalDateTime> seancesList = new ArrayList<>(seances.length);

            for (var seanceDate : seances) {
                if (!seanceDate.isEmpty()) {
                    seancesList.add(LocalDateTime.parse(seanceDate, dateFormat));
                }
            }

            System.out.println("Parsed " + s + " into " + name + " | " + genre + " | " + price + " | " + rating + " | " + seancesList);

            return new Cinema(name, genre, seancesList, price, rating);
        }
        throw new IllegalArgumentException("Invalid format: " + s);
    }

    public boolean hasMoreThanFiveSeances() {
        return seances.size() > 5;
    }

    @Override
    public String toString() {
        return title + "(" + genre.name().toLowerCase() + "-" + pricePerPerson + "eur – " + rating + ")";
    }
}
