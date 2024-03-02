package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.User;

import java.util.Collections;
import java.util.List;

public abstract class UserDAO {

    protected final List<User> users;

    protected int lastSavedHash = -1;

    UserDAO() {
        this.users = readUsers();
    }

    @NotNull
    protected abstract List<User> readUsers();

    @NotNull
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public abstract boolean addUser(@NotNull User user);

    public abstract boolean removeUser(@NotNull User user);

    public boolean isLatestVersionSaved() {
        return lastSavedHash == users.hashCode();
    }

}
