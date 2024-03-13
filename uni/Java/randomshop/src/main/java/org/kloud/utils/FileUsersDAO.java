package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.HashedString;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.readObject;

public class FileUsersDAO extends BasicFileDAO<User> {

    @Override
    @NotNull
    protected String getFilePath() {
        return "users.dat";
    }

    @Override
    protected @NotNull List<User> readObjects() {
        ArrayList<User> users = readObject(getFilePath(), new ArrayList<>());
        boolean has_admin = false;
        for (var user : users) {
            has_admin = user.id == User.ADMIN_ID;
            if (has_admin) {
                break;
            }
        }
        if (!has_admin) {
            var adminUser = new Manager(User.ADMIN_ID);
            adminUser.name.set("Default");
            adminUser.surname.set("User");
            adminUser.login.set("admin");
            adminUser.isAdmin.set(true);
            adminUser.pass.set(new HashedString("admin123"));
            users.add(adminUser);
        }
        postRead(users);
        lastSavedHash = users.hashCode();
        return users;
    }
}
