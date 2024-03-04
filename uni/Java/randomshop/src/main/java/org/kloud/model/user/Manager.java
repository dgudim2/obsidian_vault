package org.kloud.model.user;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;

import java.util.List;

public class Manager extends User {

    public static String NAME = "Manager";

    public final Field<Boolean> isAdmin = new Field<>("Admin", false, Boolean.class, __ -> "");

    @Override
    public @NotNull List<Field<?>> getFields() {
        var fields = super.getFields();
        fields.add(isAdmin);
        return fields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " " + surname;
    }
}
