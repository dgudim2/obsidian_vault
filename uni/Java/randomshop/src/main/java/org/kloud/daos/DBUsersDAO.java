package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.user.Customer;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;

import java.util.List;

import static org.kloud.utils.Utils.createDefaultUser;

public class DBUsersDAO extends BasicDBDAO<User> {
    @Override
    protected @NotNull String getTableName() {
        return "users";
    }

    @Override
    protected @NotNull List<? extends User> getStoredClasses() {
        return List.of(new Manager(), new Customer());
    }

    @Override
    protected @NotNull List<User> readObjectsInternal() {
        return createDefaultUser(super.readObjectsInternal());
    }
}
