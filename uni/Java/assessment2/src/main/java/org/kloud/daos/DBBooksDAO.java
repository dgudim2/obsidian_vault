package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Book;
import org.kloud.utils.Utils;

import java.util.List;

public class DBBooksDAO extends BasicDBDAO<Book> {


    @Override
    protected @NotNull String getTableName() {
        return "books";
    }

    @Override
    protected @NotNull List<? extends Book> getStoredClasses() {
        return List.of(new Book());
    }

    @Override
    protected @NotNull List<Book> readObjectsInternal() {
        return Utils.createDefaultBooks(super.readObjectsInternal());
    }
}
