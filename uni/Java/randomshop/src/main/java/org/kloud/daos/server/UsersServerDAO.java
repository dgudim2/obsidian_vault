package org.kloud.daos.server;

import org.kloud.model.user.User;

public class UsersServerDAO extends BasicServerDAO<User> {
    @Override
    protected String getEndpoint() {
        return "api/users";
    }
}
