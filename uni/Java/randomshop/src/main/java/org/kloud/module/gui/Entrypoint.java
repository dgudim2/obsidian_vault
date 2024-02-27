package org.kloud.module.gui;

import atlantafx.base.theme.Dracula;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Entrypoint extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(Entrypoint.class.getResource("main-view.fxml"));
        stage.setTitle("Randomshop");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public static void launch(@NotNull String[] args) {
        Application.launch(args);
    }
}