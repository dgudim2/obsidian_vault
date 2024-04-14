package org.kloud.flowcontrollers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.common.UserCapability;
import org.kloud.daos.BasicDAO;
import org.kloud.model.user.User;
import org.kloud.utils.Logger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class LoginController {

    public final ObjectProperty<User> loggedInUser = new SimpleObjectProperty<>(null);
    private Set<UserCapability> userCaps = new HashSet<>();
    protected final BasicDAO<User> userDAO;

    public LoginController(BasicDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    public boolean tryLogin(String login, String pass) {
        var usr = checkUserLogin(login, pass);
        if (usr != null) {
            // NOTE: It's important to load user capabilities before setting the user which may contain listeners
            userCaps = usr.getUserCaps();
            loggedInUser.set(usr);
            Logger.success("LOGGED IN AS " + usr);
        }
        return usr != null;
    }

    public void logout() {
        var usr = loggedInUser.get();
        if (usr != null) {
            loggedInUser.set(null);
            userCaps = null;
            Logger.success(usr + " LOGGED OUT");
        }
    }

    @Nullable
    protected abstract User checkUserLogin(String user, String pass);

    public boolean hasUserCapability(@NotNull UserCapability capability) {
        return userCaps.contains(capability);
    }

    public boolean isLoggedInUser(@Nullable User user) {
        if(user == null) {
            return false;
        }
        return Objects.equals(user.id, loggedInUser.get().id);
    }
}
