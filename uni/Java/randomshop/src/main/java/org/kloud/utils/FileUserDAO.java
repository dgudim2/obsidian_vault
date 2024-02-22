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
    public List<User> readUsers() {
        return readObject(FILE_PATH, new ArrayList<>());
    }

    @Override
    public boolean writeUsers(@NotNull List<User> users) {
        return writeObject(users, FILE_PATH);
    }
}
