package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.User;

import java.util.List;

public abstract class UserDAO {

    @Nullable
    public abstract List<User> readUsers();
    public abstract boolean writeUsers(@NotNull List<User> users);

}
