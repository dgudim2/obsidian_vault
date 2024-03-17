package org.kloud.utils;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigurationSingleton {

    public enum StorageBackend {
        LOCAL, DB, REMOTE_SERVER
    }

    public final String serverAddress;
    public final String dbAddress;

    public final StorageBackend storageBackend;

    private static ConfigurationSingleton instance;

    private ConfigurationSingleton() {

        String serverAddress;
        String dbAddress;
        StorageBackend storageBackend;

        try {
            var jsonObject = JsonParser.parseReader(new FileReader("config.json")).getAsJsonObject();
            serverAddress = jsonObject.get("server_address").getAsString();
            dbAddress = jsonObject.get("db_address").getAsString();
            storageBackend = StorageBackend.valueOf(jsonObject.get("backend").getAsString());
        } catch (FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
            serverAddress = "";
            dbAddress = "";
            storageBackend = StorageBackend.LOCAL;
        }

        this.dbAddress = dbAddress;
        this.serverAddress = serverAddress;
        this.storageBackend = storageBackend;
    }

    public static ConfigurationSingleton getInstance() {
        if(instance == null) {
            instance = new ConfigurationSingleton();
        }
        return instance;
    }

}
