package org.kloud;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("randomshop")
                .build()
                .description("Launch the shop server/admin cli");
        parser.addArgument("--mode").dest("mode").type(String.class).choices("cli", "gui", "server").setDefault("gui");

        try {
            Namespace res = parser.parseArgs(args);
            String mode = res.get("mode");
            switch (mode) {
                case "cli" -> org.kloud.module.cli.Entrypoint.launch(args);
                case "server" -> org.kloud.module.server.Entrypoint.launch(args);
                case "gui" ->org.kloud.module.gui.Entrypoint.launch(args);
                default -> throw new IndexOutOfBoundsException("Mode " + mode + " is unknown");
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}