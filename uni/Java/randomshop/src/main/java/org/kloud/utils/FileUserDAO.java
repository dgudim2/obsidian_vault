package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUserDAO extends UserDAO {

    private static final String FILE_PATH = "users.dat";

    @SuppressWarnings("unchecked")
    @Override
    public List<User> readUsers() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (ArrayList<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean writeUsers(@NotNull List<User> users) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            oos.writeObject(users);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
