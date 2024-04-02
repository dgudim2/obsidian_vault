package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.user.User;

import java.util.List;

import static org.kloud.utils.Utils.createDefaultUser;

public class FileUsersDAO extends BasicFileDAO<User> {

    @Override
    @NotNull
    protected String getFilePath() {
        return "users.dat";
    }

    @Override
    protected @NotNull List<User> readObjectsInternal() {
        return createDefaultUser(super.readObjectsInternal());
    }
}
