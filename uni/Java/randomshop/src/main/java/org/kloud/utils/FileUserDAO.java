package org.kloud.utils;

import org.kloud.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUserDAO extends UserDAO {

    private static final String FILE_PATH = "users.dat";

    @SuppressWarnings("unchecked")
    @Override
    public List<User> readUsers() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))){
            return (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> writeUsers() {
        return null;
    }
}
