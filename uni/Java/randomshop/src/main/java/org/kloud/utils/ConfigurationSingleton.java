package org.kloud.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.Nullable;
import org.kloud.backends.AbstractBackend;
import org.kloud.backends.DBBackend;
import org.kloud.backends.LocalBackend;
import org.kloud.flowcontrollers.LoginController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * A singleton class holding all application state and config
 */
public class ConfigurationSingleton {

    public static final List<Class<? extends AbstractBackend>> storageBackends = List.of(LocalBackend.class, DBBackend.class);

    private static class Fields {
        static final String DB_ADDRESS = "db_address";
        static final String DB_USER = "db_user";
        static final String DB_PASSWORD = "db_password";
        static final String SERVER_ADDRESS = "server_address";
        static final String BACKEND_TYPE = "backend_type";
        static final String LOG_LEVEL = "log_level";
    }

    public final StringProperty serverAddress;
    public final StringProperty dbAddress;
    public final StringProperty dbUser;
    public final StringProperty dbPassword;
    public final ObjectProperty<Logger.Loglevel> targetLogLevel;

    public final ObjectProperty<AbstractBackend> storageBackend;

    private static ConfigurationSingleton instance;

    private static final String CONFIG_PATH = "config.json";

    private ConfigurationSingleton() {

        AbstractBackend storageBackend = null;

        JsonObject jsonObject = null;

        try {
            jsonObject = JsonParser.parseReader(new FileReader(CONFIG_PATH)).getAsJsonObject();
        } catch (FileNotFoundException | JsonSyntaxException e) {
            System.out.println("--> Config corrupted or not found");
        }

        this.dbAddress = new SimpleStringProperty(getAsString(jsonObject, Fields.DB_ADDRESS, "jdbc:postgresql://localhost/postgres"));
        this.dbUser = new SimpleStringProperty(getAsString(jsonObject, Fields.DB_USER, "postgres"));
        this.dbPassword = new SimpleStringProperty(getAsString(jsonObject, Fields.DB_PASSWORD, ""));
        this.serverAddress = new SimpleStringProperty(getAsString(jsonObject, Fields.SERVER_ADDRESS, "localhost"));
        try {
            storageBackend = storageFromClass(Class.forName(getAsString(jsonObject, Fields.BACKEND_TYPE, LocalBackend.class.getName())));
        } catch (ClassNotFoundException e) {
            // Can't do this -> ErrorHandler.displayException(e).handleDefault();
            // This causes a circular dependency in the case of an exception: ErrorHandler -> Logger -> ConfigurationSingleton -> ErrorHandler
            e.printStackTrace();
        }
        this.storageBackend = new SimpleObjectProperty<>(storageBackend == null ? new LocalBackend() : storageBackend);
        this.targetLogLevel = new SimpleObjectProperty<>(Logger.Loglevel.valueOf(getAsString(jsonObject, Fields.LOG_LEVEL, Logger.Loglevel.DEBUG.name())));

        System.out.println("Config loaded");
    }

    private String getAsString(@Nullable JsonObject jsonObject, String field, String defaultValue) {
        if(jsonObject == null) {
            return defaultValue;
        }
        return jsonObject.has(field) ? jsonObject.get(field).getAsString() : defaultValue;
    }

    public static AbstractBackend storageFromClass(Class<?> klass) {
        try {
            return (AbstractBackend) klass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            ErrorHandler.displayException(e).handleDefault();
            return null;
        }
    }

    public static void writeConfig() {
        try {
            var inst = getInstance();
            var writer = new JsonWriter(new FileWriter(CONFIG_PATH));
            writer.beginObject();
            writer.name(Fields.SERVER_ADDRESS).value(inst.serverAddress.getValue());
            writer.name(Fields.DB_ADDRESS).value(inst.dbAddress.getValue());
            writer.name(Fields.DB_USER).value(inst.dbUser.getValue());
            writer.name(Fields.DB_PASSWORD).value(inst.dbPassword.getValue());
            writer.name(Fields.BACKEND_TYPE).value(inst.storageBackend.get().getClass().getName());
            writer.name(Fields.LOG_LEVEL).value(inst.targetLogLevel.getValue().name());
            writer.endObject();
            writer.close();
            Logger.success("Config saved");
        } catch (IOException e) {
            ErrorHandler.displayException(e).handleDefault();
        }
    }

    public static ConfigurationSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigurationSingleton();
        }
        return instance;
    }

    public static AbstractBackend getStorage() {
        return getInstance().storageBackend.get();
    }

    public static LoginController getLoginController() {
        return getStorage().getLoginController();
    }

    public static boolean isValid() {
        return getStorage().isValid();
    }

    public static void close() {
        getStorage().close();
    }
}
