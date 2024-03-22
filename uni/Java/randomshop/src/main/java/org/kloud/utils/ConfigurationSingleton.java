package org.kloud.utils;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConfigurationSingleton {

    private static final List<Class<? extends AbstractStorage>> storageBackends = List.of(LocalStorage.class);

    private class
    Fields {
        static final String DB_ADDRESS = "db_address";
        static final String SERVER_ADDRESS = "server_address";
        static final String BACKEND_TYPE = "backend_type";
    }

    public final StringProperty serverAddress;
    public final StringProperty dbAddress;

    public final ObjectProperty<AbstractStorage> storageBackend;

    private static ConfigurationSingleton instance;

    private static final String CONFIG_PATH = "config.json";

    private ConfigurationSingleton() {

        String serverAddress;
        String dbAddress;
        AbstractStorage storageBackend;

        try {
            var jsonObject = JsonParser.parseReader(new FileReader(CONFIG_PATH)).getAsJsonObject();
            serverAddress = jsonObject.get(Fields.SERVER_ADDRESS).getAsString();
            dbAddress = jsonObject.get(Fields.DB_ADDRESS).getAsString();
            storageBackend = (AbstractStorage) Class.forName((jsonObject.get(Fields.BACKEND_TYPE).getAsString())).getDeclaredConstructor().newInstance();
        } catch (FileNotFoundException | JsonSyntaxException | IllegalStateException | NullPointerException |
                 ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
            serverAddress = "";
            dbAddress = "";
            storageBackend = new LocalStorage();
        }

        this.dbAddress = new SimpleStringProperty(dbAddress);
        this.serverAddress = new SimpleStringProperty(serverAddress);
        this.storageBackend = new SimpleObjectProperty<>(storageBackend);
    }

    public static void writeConfig() throws IOException {
        var inst = getInstance();
        JsonWriter  writer = new JsonWriter(new FileWriter(CONFIG_PATH));
        writer.beginObject();
        writer.name(Fields.SERVER_ADDRESS).value(inst.serverAddress.getValue());
        writer.name(Fields.DB_ADDRESS).value(inst.dbAddress.getValue());
        writer.name(Fields.BACKEND_TYPE).value(LocalStorage.class.getName());
        writer.endObject();
        writer.close();
    }

    public static ConfigurationSingleton getInstance() {
        if(instance == null) {
            instance = new ConfigurationSingleton();
        }
        return instance;
    }

}
