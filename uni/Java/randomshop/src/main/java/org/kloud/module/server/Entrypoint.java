package org.kloud.module.server;

import org.jetbrains.annotations.NotNull;
import org.kloud.backends.LocalBackend;
import org.kloud.utils.Conf;
import org.kloud.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Entrypoint {
    public static void launch(@NotNull String[] args) throws IOException {
        if (!Conf.getInstance().storageBackend.get().getClass().equals(LocalBackend.class)) {
            Logger.warn("""
                    \033[0;33m
                    =====================================================
                    |                                                   |
                    |      Only Local backend is supported for now      |
                    |             Switching to LocalBackend             |
                    |                                                   |
                    =====================================================\033[0m""");
            Conf.getInstance().storageBackend.set(new LocalBackend());
        }
        SpringApplication.run(Entrypoint.class, args);
    }
}
