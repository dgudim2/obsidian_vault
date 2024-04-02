package org.kloud.module.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Logger;

import java.util.List;

public class SettingsController implements BaseController {

    @FXML
    public TextField dbAddressInput;
    @FXML
    public ComboBox<Logger.Loglevel> logLevelSelector;
    @FXML
    public TextField dbUsername;
    @FXML
    public TextField dbPass;

    @FXML
    public void initialize() {
        var config = ConfigurationSingleton.getInstance();

        dbAddressInput.textProperty().bindBidirectional(config.dbAddress);
        dbUsername.textProperty().bindBidirectional(config.dbUser);
        dbPass.textProperty().bindBidirectional(config.dbPassword);

        logLevelSelector.setItems(FXCollections.observableList(List.of(Logger.Loglevel.values())));
        logLevelSelector.getSelectionModel().select(config.targetLogLevel.get());
        config.targetLogLevel.bind(logLevelSelector.getSelectionModel().selectedItemProperty());
    }

    @Override
    public boolean notifyCloseRequest() {
        ConfigurationSingleton.writeConfig();
        // TODO: Test connection to db/etc
        return true;
    }
}
