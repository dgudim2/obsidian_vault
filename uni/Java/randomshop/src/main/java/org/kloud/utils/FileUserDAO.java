package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.readObject;
import static org.kloud.utils.Utils.writeObject;

public class FileUserDAO extends UserDAO {

    private static final String FILE_PATH = "users.dat";

    @Override
    protected @NotNull List<User> readUsers() {
        List<User> users = readObject(FILE_PATH, new ArrayList<>());
        lastSavedHash = users.hashCode();
        return users;
    }

    @Override
    public boolean addUser(@NotNull User user) {
        if(users.contains(user)) {
            return false;
        }
        users.add(user);
        return writeUsers();
    }

    @Override
    public boolean removeUser(@NotNull User user) {
        var removed = users.remove(user);
        if(removed) {
            return writeUsers();
        }
        return false;
    }

    private boolean writeUsers() {
        lastSavedHash = users.hashCode();
        return writeObject(users, FILE_PATH);
    }
}
