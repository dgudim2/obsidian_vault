package org.kloud.module.server;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Entrypoint {
    public static void launch(@NotNull String[] args) throws IOException {
        SpringApplication.run(Entrypoint.class, args);
    }
}
