package org.kloud.module.gui;

import atlantafx.base.theme.Dracula;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.kloud.utils.Utils;

import java.io.IOException;

/**
 * Main UI entrypoint
 */
public class Entrypoint extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        Utils.loadStage(stage, "main-view.fxml", "Randomshop").show();
    }

    public static void launch(@NotNull String[] args) {
        Application.launch(args);
    }
}