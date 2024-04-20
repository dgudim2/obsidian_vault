package org.kloud.module.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.kloud.utils.Conf;
import org.kloud.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for settings screen
 */
public class SettingsController implements BaseController {
    @FXML
    public TextField serverAddressInput;

    @FXML
    public TextField dbAddressInput;
    @FXML
    public ComboBox<String> storageBackendSelector;
    public ComboBox<Logger.Loglevel> logLevelSelector;
    public TextField dbUsername;
    public TextField dbPass;

    private final AtomicReference<String> newSelectedBackend = new AtomicReference<>();
    private final List<String> backendNames = new ArrayList<>();

    @FXML
    public void initialize() {
        var config = Conf.getInstance();

        backendNames.clear();
        backendNames.addAll(Conf.storageBackends
                .stream().map(Conf::getBackendName).toList());

        var currentBackend = config.getCurrentBackendName();
        var loadedBackendIndex = backendNames.indexOf(currentBackend);
        newSelectedBackend.set(currentBackend);

        storageBackendSelector.setItems(FXCollections.observableList(backendNames));
        storageBackendSelector.getSelectionModel().select(Math.max(loadedBackendIndex, 0));
        storageBackendSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> newSelectedBackend.set(newValue));

        serverAddressInput.textProperty().bindBidirectional(config.serverAddress);
        dbAddressInput.textProperty().bindBidirectional(config.dbAddress);
        dbUsername.textProperty().bindBidirectional(config.dbUser);
        dbPass.textProperty().bindBidirectional(config.dbPassword);

        logLevelSelector.setItems(FXCollections.observableList(List.of(Logger.Loglevel.values())));
        logLevelSelector.getSelectionModel().select(config.targetLogLevel.get());
        config.targetLogLevel.bind(logLevelSelector.getSelectionModel().selectedItemProperty());
    }

    @Override
    public boolean notifyCloseRequest() {

        if (!Objects.equals(newSelectedBackend.get(), Conf.getInstance().getCurrentBackendName())) {
            Alert closeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            closeDialog.setTitle("Confirm exit");
            closeDialog.setHeaderText("Changing the backend requires app restart");
            closeDialog.setContentText("The app is going to exit now");
            var result = closeDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Conf.getInstance().storageBackend.set(
                        Conf.storageFromClass(
                                Conf.storageBackends.get(backendNames.indexOf(newSelectedBackend.get()))));
                Conf.writeConfig();
                System.exit(0);
            } else {
                return false;
            }
        }
        Conf.writeConfig();
        return Conf.isValid();
    }
}
