package org.kloud.module.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.kloud.utils.ConfigurationSingleton;

public class SettingsController {
    @FXML
    public TextField serverAddressInput;

    @FXML
    public TextField dbAddressInput;
    @FXML
    public ComboBox<String> storageBackendSelector;

    @FXML
    public void initialize() {
        var config = ConfigurationSingleton.getInstance();

        serverAddressInput.textProperty().bindBidirectional(config.serverAddress);
        dbAddressInput.textProperty().bindBidirectional(config.dbAddress);
    }
}
