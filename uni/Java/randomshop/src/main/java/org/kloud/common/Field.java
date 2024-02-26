package org.kloud.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class Field<T> {

    public final String name;
    @Nullable
    public T value = null;
    public final boolean required;
    private final Function<T, String> validator;
    public final Class<T> klass;

    public Field(@NotNull String name, boolean required, @NotNull Class<T> klass, @NotNull Function<T, String> validator) {
        this.name = name;
        this.klass = klass;
        this.required = required;
        this.validator = validator;
    }

    public T get() {
        return value;
    }

    @Nullable
    public String set(T value) {
        var validatorMessage = validator.apply(value);
        if (!validatorMessage.isEmpty()) {
            return validatorMessage;
        }
        this.value = value;
        return null;
    }

    // TODO: get fx backing field, cli field
}
