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
    public TextField serverAddressInput;

    @FXML
    public TextField dbAddressInput;
    @FXML
    public ComboBox<String> storageBackendSelector;
    public ComboBox<Logger.Loglevel> logLevelSelector;

    @FXML
    public void initialize() {
        var config = ConfigurationSingleton.getInstance();

        storageBackendSelector.setItems(FXCollections.observableList(ConfigurationSingleton.storageBackends.stream().map(Class::getSimpleName).toList()));

        var loadedBackendIndex = ConfigurationSingleton.storageBackends.indexOf(config.storageBackend.get().getClass());

        storageBackendSelector.getSelectionModel().select(Math.max(loadedBackendIndex, 0));
        storageBackendSelector.getSelectionModel().selectedIndexProperty().map(index ->
                        ConfigurationSingleton.storageFromClass(ConfigurationSingleton.storageBackends.get(Math.max(index.intValue(), 0))))
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        config.storageBackend.set(newValue);
                    }
                });

        serverAddressInput.textProperty().bindBidirectional(config.serverAddress);
        dbAddressInput.textProperty().bindBidirectional(config.dbAddress);

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
