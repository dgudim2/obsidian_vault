package org.kloud.module.server;

import org.jetbrains.annotations.NotNull;
import org.kloud.backends.LocalBackend;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Entrypoint {
    public static void launch(@NotNull String[] args) throws IOException {
        if(!ConfigurationSingleton.getInstance().storageBackend.get().getClass().equals(LocalBackend.class)) {
            Logger.error("Only Local backend is supported for now");
            System.exit(1);
        }
        SpringApplication.run(Entrypoint.class, args);
    }
}
