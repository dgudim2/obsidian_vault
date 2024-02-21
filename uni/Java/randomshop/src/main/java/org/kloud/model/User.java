package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class User implements Serializable {

    protected long uid;
    protected @NotNull String name;
    protected @NotNull String surname;
    protected @Nullable String cardNumber;

    public User(@NotNull String name, @NotNull String surname, @Nullable String cardNumber) {
        this.name = name;
        this.surname = surname;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
