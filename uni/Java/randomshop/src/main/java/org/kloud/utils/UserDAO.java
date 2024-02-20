package org.kloud.utils;

import org.kloud.model.User;

import java.util.List;

public abstract class UserDAO {

    public abstract List<User> readUsers();
    public abstract List<User> writeUsers();

}
