package org.kloud.utils;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class Utils {

    public static boolean writeObject(@NotNull Object object, @NotNull String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            System.out.println("Object saved: " + object);
            oos.writeObject(object);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readObject(@NotNull String path, @Nullable T defaultValue) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            T obj = (T) ois.readObject();
            System.out.println("Object loaded: " + obj);
            return obj;
        } catch (FileNotFoundException e) {
            return defaultValue;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void setDanger(@NotNull Node node, boolean active) {
        node.pseudoClassStateChanged(PseudoClass.getPseudoClass("danger"), active);
        if (active) {
            if (!node.getStyleClass().contains("danger")) {
                node.getStyleClass().add("danger");
            }
        } else {
            node.getStyleClass().remove("danger");
        }
    }

    public static String testLength(String val, int min, int max) {
        if (val.length() < min) {
            return "Length should be >= " + min;
        }
        if (val.length() > max) {
            return "Length should be <= " + max;
        }
        return "";
    }

}
