package org.kloud.model.user;

import org.jetbrains.annotations.NotNull;

public class Customer extends User {

    public static String NAME = "Customer";

    protected Customer() {
    }

    public Customer(long id) {
        super(id);
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " " + surname;
    }
}
