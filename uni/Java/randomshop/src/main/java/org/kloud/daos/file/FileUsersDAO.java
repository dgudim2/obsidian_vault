package org.kloud.daos.file;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.user.User;

import java.util.List;

import static org.kloud.utils.Utils.createDefaultUser;

/**
 * {@link BasicFileDAO} for {@link User Users}
 */
public class FileUsersDAO extends BasicFileDAO<User> {

    @Override
    @NotNull
    protected String getFileName() {
        return "users.dat";
    }

    @Override
    protected @NotNull List<User> readObjectsInternal() {
        return createDefaultUser(super.readObjectsInternal());
    }
}
