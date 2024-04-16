package org.kloud.model.user;

import org.jetbrains.annotations.NotNull;

public class Customer extends User {

    public static final String NAME = "Customer";

    public Customer() {
        super();
    }

    @Override
    public String isSafeToDelete() {
        return "";
    }

    public Customer(long id) {
        super(id);
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " " + surname;
    }
}
