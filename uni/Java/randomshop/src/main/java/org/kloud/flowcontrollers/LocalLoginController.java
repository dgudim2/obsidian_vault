package org.kloud.flowcontrollers;

import org.jetbrains.annotations.Nullable;
import org.kloud.daos.BasicDAO;
import org.kloud.model.user.User;

public class LocalLoginController extends LoginController {
    public LocalLoginController(BasicDAO<User> userDAO) {
        super(userDAO);
    }

    // TODO: Move into userDAO (as an interface maybe)

    @Override
    protected @Nullable User checkUserLogin(String login, String password) {
        var users = userDAO.getObjects();
        for (var user : users) {
            if (user.login.get().equals(login)) {
                if (user.checkPassword(password)) {
                    return user;
                }
                return null;
            }
        }
        return null;
    }
}
