package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.model.BaseModel;
import org.kloud.model.enums.BookGenre;
import org.kloud.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book extends BaseModel {

    public static String NAME = "Book";

    public final Field<String> title = new Field<>("Title", true, String.class, v -> Utils.testLength(v, 3, 100));

    public final Field<Integer> publishYear = new Field<>("Publish year", true, Integer.class, i -> Utils.testBounds(i, 1700, 2024));

    public final Field<Integer> issn = new Field<>("ISSN", true, Integer.class, __ -> "");

    public final Field<String> authors = new Field<>("Authors", true, String.class, v -> Utils.testLength(v, 3, 100));

    public final Field<BookGenre> genre = new Field<>("Genre", true, BookGenre.class, __ -> "");

    public final Field<Boolean> isAvailable = new Field<>("Is available", true, Boolean.class, __ -> "");

    public Book() {
        super();
    }

    public Book(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        var fields = new ArrayList<Field<?>>();
        fields.add(title);
        fields.add(publishYear);
        fields.add(issn);
        fields.add(authors);
        fields.add(genre);
        fields.add(isAvailable);
        return fields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + title + " (" + authors + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(publishYear, book.publishYear) && Objects.equals(issn, book.issn) && Objects.equals(authors, book.authors) && Objects.equals(genre, book.genre) && Objects.equals(isAvailable, book.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publishYear, issn, authors, genre, isAvailable);
    }
}
