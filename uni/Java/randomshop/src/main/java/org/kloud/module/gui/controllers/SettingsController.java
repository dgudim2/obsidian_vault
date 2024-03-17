package org.kloud.module.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    public TextField serverAddressInput;
    @FXML
    public ComboBox<String> storageBackendSelector;
}
