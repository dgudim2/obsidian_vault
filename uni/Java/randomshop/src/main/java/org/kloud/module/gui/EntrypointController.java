package org.kloud.module.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EntrypointController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
