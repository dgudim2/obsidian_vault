package org.kloud.model.user;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Customer extends User {

    @EqualsAndHashCode.Exclude
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
