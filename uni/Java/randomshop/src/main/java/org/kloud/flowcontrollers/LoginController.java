package org.kloud.flowcontrollers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.Nullable;
import org.kloud.daos.BasicDAO;
import org.kloud.model.user.User;
import org.kloud.utils.Logger;

public abstract class LoginController {

    public final ObjectProperty<User> loggedInUser = new SimpleObjectProperty<>(null);
    protected final BasicDAO<User> userDAO;

    public LoginController(BasicDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    public boolean tryLogin(String login, String pass) {
        var usr = checkUserLogin(login, pass);
        if (usr != null) {
            loggedInUser.set(usr);
            Logger.success("LOGGED IN AS " + usr);
        }
        return usr != null;
    }

    public void logout() {
        var usr = loggedInUser.get();
        if (usr != null) {
            loggedInUser.set(null);
            Logger.success(usr + " LOGGED OUT");
        }
    }

    @Nullable
    protected abstract User checkUserLogin(String user, String pass);
}
