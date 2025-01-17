package org.kloud.utils;

import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.enums.BookGenre;
import org.kloud.model.product.Book;
import org.kloud.module.gui.Entrypoint;
import org.kloud.module.gui.controllers.BaseController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.function.Consumer;

public class Utils {

    public static boolean writeObject(@NotNull Object object, @NotNull String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(object);
            Logger.info("Object saved: " + object);
            return true;
        } catch (IOException e) {
            ErrorHandler.displayException(e).handleDefault();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> T readObject(@NotNull String path, @NotNull T defaultValue) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            T obj = (T) ois.readObject();
            Logger.info("Object loaded: " + obj);
            return obj;
        } catch (FileNotFoundException e) {
            return defaultValue;
        } catch (IOException | ClassNotFoundException e) {
            ErrorHandler.displayException(e).handleDefault();
            return defaultValue;
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

    public static String testBounds(int val, int min, int max) {
        if (val < min) {
            return "Value should be >= " + min;
        }
        if (max != -1 && val > max) {
            return "Value should be <= " + max;
        }
        return "";
    }

    public static String testBounds(double val, double min, double max) {
        if (val < min) {
            return "Value should be >= " + min;
        }
        if (max != -1 && val > max) {
            return "Value should be <= " + max;
        }
        return "";
    }

    public static String testBounds(long val, long min, long max) {
        if (val < min) {
            return "Value should be >= " + min;
        }
        if (max != -1 && val > max) {
            return "Value should be <= " + max;
        }
        return "";
    }

    public static String testLength(String val, int min, int max) {
        if (val.length() < min) {
            return "Length should be >= " + min;
        }
        if (max != -1 && val.length() > max) {
            return "Length should be <= " + max;
        }
        return "";
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder hexStringBuffer = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    public static String hashPass(String pass, String salt) {

        String hashValue;

        KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            hashValue = bytesToHexStr(factory.generateSecret(spec).getEncoded());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return hashValue;
    }

    @NotNull
    public static Stage loadStage(@NotNull Stage stage, String filename, String title, Consumer<Stage> initConsumer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Entrypoint.class.getResource(filename));
        stage.setTitle(title);
        stage.setScene(new Scene(fxmlLoader.load()));
        initConsumer.accept(stage);
        stage.setOnCloseRequest(we -> {
            BaseController controller = fxmlLoader.getController();
            if (!controller.notifyCloseRequest()) {
                we.consume();
            }
        });
        stage.show();
        return stage;
    }

    @NotNull
    public static Stage loadStage(@NotNull Stage stage, String filename, String title) throws IOException {
        return loadStage(stage, filename, title, stage1 -> {});
    }

    @NotNull
    public static List<Book> createDefaultBooks(@NotNull List<Book> books) {
        if(books.isEmpty()) {
            var book1 = new Book();
            book1.authors.set("Author1,Author2");
            book1.genre.set(BookGenre.HORROR);
            book1.title.set("Title1");
            book1.issn.set(1000);
            book1.isAvailable.set(true);
            book1.publishYear.set(2000);

            var book2 = new Book();
            book2.authors.set("Author2,Author3");
            book2.genre.set(BookGenre.ADVENTURE);
            book2.title.set("Title2");
            book2.issn.set(2000);
            book2.isAvailable.set(true);
            book2.publishYear.set(2001);

            var book3 = new Book();
            book3.authors.set("Author3,Author4");
            book3.genre.set(BookGenre.SCI_FI);
            book3.title.set("Title3");
            book3.issn.set(3000);
            book3.isAvailable.set(false);
            book3.publishYear.set(2002);

            books.add(book1);
            books.add(book2);
            books.add(book3);
        }
        return books;
    }
}
