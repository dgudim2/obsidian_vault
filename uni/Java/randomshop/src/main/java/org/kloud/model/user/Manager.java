package org.kloud.model.user;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;

import java.util.List;
import java.util.Objects;

public class Manager extends User {

    public static String NAME = "Manager";

    public final Field<Boolean> isAdmin = new Field<>("Admin", false, false, Boolean.class, __ -> "");

    public Manager() {

    }

    public Manager(long id) {
        super(id);
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Manager manager = (Manager) object;
        return Objects.equals(isAdmin, manager.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isAdmin);
    }
}
