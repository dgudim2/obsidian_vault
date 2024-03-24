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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConfigurationSingleton {

    public static final List<Class<? extends AbstractStorage>> storageBackends = List.of(LocalStorage.class);

    private static class Fields {
        static final String DB_ADDRESS = "db_address";
        static final String SERVER_ADDRESS = "server_address";
        static final String BACKEND_TYPE = "backend_type";
        static final String LOG_LEVEL = "log_level";
    }

    public final StringProperty serverAddress;
    public final StringProperty dbAddress;
    public final ObjectProperty<Logger.Loglevel> targetLogLevel;

    public final ObjectProperty<AbstractStorage> storageBackend;

    private static ConfigurationSingleton instance;

    private static final String CONFIG_PATH = "config.json";

    private ConfigurationSingleton() {

        AbstractStorage storageBackend = null;

        JsonObject jsonObject = null;

        try {
            jsonObject = JsonParser.parseReader(new FileReader(CONFIG_PATH)).getAsJsonObject();
        } catch (FileNotFoundException | JsonSyntaxException e) {
            System.out.println("--> Config corrupted or not found");
        }

        this.dbAddress = new SimpleStringProperty(getAsString(jsonObject, Fields.DB_ADDRESS, ""));
        this.serverAddress = new SimpleStringProperty(getAsString(jsonObject, Fields.SERVER_ADDRESS, ""));
        try {
            storageBackend = storageFromClass(Class.forName(getAsString(jsonObject, Fields.BACKEND_TYPE, LocalStorage.class.getName())));
        } catch (ClassNotFoundException e) {
            // Can't do this -> ErrorHandler.displayException(e).handleDefault();
            // This causes a circular dependency in the case of en exception ErrorHandler -> Logger -> ConfigurationSingleton -> ErrorHandler
            e.printStackTrace();
        }
        this.storageBackend = new SimpleObjectProperty<>(storageBackend == null ? new LocalStorage() : storageBackend);
        this.targetLogLevel = new SimpleObjectProperty<>(Logger.Loglevel.valueOf(getAsString(jsonObject, Fields.LOG_LEVEL, Logger.Loglevel.DEBUG.name())));

        System.out.println("Config loaded");
    }

    private String getAsString(@Nullable JsonObject jsonObject, String field, String defaultValue) {
        if(jsonObject == null) {
            return defaultValue;
        }
        return jsonObject.has(field) ? jsonObject.get(field).getAsString() : defaultValue;
    }

    public static AbstractStorage storageFromClass(Class<?> klass) {
        try {
            return (AbstractStorage) klass.getDeclaredConstructor().newInstance();
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
            writer.name(Fields.BACKEND_TYPE).value(inst.storageBackend.get().getClass().getName());
            writer.name(Fields.LOG_LEVEL).value(inst.targetLogLevel.getValue().name());
            writer.endObject();
            writer.close();
            Logger.info("Config saved");
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
}
