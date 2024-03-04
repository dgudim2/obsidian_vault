package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.user.User;

public class FileUsersDAO extends BasicFileDAO<User> {

    @Override
    @NotNull
    protected String getFilePath() {
        return "users.dat";
    }
}
