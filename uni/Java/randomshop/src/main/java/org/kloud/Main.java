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
        parser.addArgument("--gui").dest("is_gui").type(Boolean.class).setDefault(false);

        try {
            Namespace res = parser.parseArgs(args);
            if (res.get("is_gui")) {
                // TODO: handle gui part
                org.kloud.module.gui.Entrypoint.launch();
            } else {
                org.kloud.module.cli.Entrypoint.launch();
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}