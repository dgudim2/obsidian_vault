package org.kloud.module.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kloud.common.HashedString;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.module.gui.Entrypoint;
import org.kloud.module.gui.TabWrapper;
import org.kloud.utils.DaoSingleton;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.kloud.utils.Utils.setDanger;

public class EntrypointController {

    @FXML
    public ListView<Product> productList;
    @FXML
    public Pane productEditArea;
    @FXML
    public Button deleteProductButton;
    @FXML
    public Button addProductButton;
    @FXML
    public Button saveProductButton;
    @FXML
    public TabPane tabs;
    @FXML
    public Tab warehousesTab;
    @FXML
    public Tab productsTab;
    @FXML
    public Tab usersTab;
    @FXML
    public Button loginButton;
    @FXML
    public TextField userField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label loginTitle;
    @FXML
    public Label invalidUserLabel;
    @FXML
    public ListView<User> userList;
    @FXML
    public Button deleteUserButton;
    @FXML
    public Button addUserButton;
    @FXML
    public Pane userEditArea;
    @FXML
    public Button saveUserButton;
    @FXML
    public Button changeUserPasswordButton;
    @FXML
    public ListView<Warehouse> warehousesList;
    @FXML
    public Button deleteWarehouseButton;
    @FXML
    public Button addWarehouseButton;
    @FXML
    public Pane warehouseEditArea;
    @FXML
    public Button saveWarehouseButton;
    @FXML
    public Button settingsButton;

    AtomicReference<User> loggedInUser = new AtomicReference<>(null);

    private TabWrapper<Product> productTabWrapper;
    private TabWrapper<User> userTabWrapper;
    private TabWrapper<Warehouse> warehouseTabWrapper;

    @FXML
    public void initialize() {
        warehousesTab.setDisable(true);
        productsTab.setDisable(true);
        usersTab.setDisable(true);

        productTabWrapper = new TabWrapper<>(
                "product",
                Product.PRODUCTS,
                DaoSingleton.getInstance().productStorage,
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton
        );

        userTabWrapper = new TabWrapper<>(
                "user",
                User.USERS,
                DaoSingleton.getInstance().userStorage,
                userEditArea,
                userList,
                deleteUserButton,
                addUserButton,
                saveUserButton
        );

        warehouseTabWrapper = new TabWrapper<>(
                "warehouse",
                List.of(Warehouse.class),
                DaoSingleton.getInstance().warehouseStorage,
                warehouseEditArea,
                warehousesList,
                deleteWarehouseButton,
                addWarehouseButton,
                saveWarehouseButton
        );

        changeUserPasswordButton.setDisable(true);
        userTabWrapper.selectedObject.addListener((observableValue, oldObject, newObject) -> changeUserPasswordButton.setDisable(newObject == null));
        changeUserPasswordButton.setOnAction(actionEvent -> {
            boolean adminLoggedInOrCurrentUser =
                    loggedInUser.get() instanceof Manager m && m.isAdmin.get()
                            || Objects.equals(loggedInUser.get(), userTabWrapper.selectedObject.get());

            Alert passwordDialog = new Alert(Alert.AlertType.CONFIRMATION);

            var oldPassLabel = new Label("Old password: ");
            var oldPassInput = new PasswordField();
            var newPassLabel = new Label("New password: ");
            var newPassInput = new PasswordField();

            var oldPassContainer = new AnchorPane();
            oldPassContainer.getChildren().addAll(oldPassLabel, oldPassInput);
            AnchorPane.setBottomAnchor(oldPassLabel, 1.0);
            AnchorPane.setTopAnchor(oldPassLabel, 1.0);
            AnchorPane.setRightAnchor(oldPassLabel, 200.0);
            AnchorPane.setRightAnchor(oldPassInput, 1.0);
            oldPassContainer.setPadding(new Insets(0, 0, 3, 0));

            var newPassContainer = new AnchorPane();
            newPassContainer.getChildren().addAll(newPassLabel, newPassInput);
            AnchorPane.setBottomAnchor(newPassLabel, 1.0);
            AnchorPane.setTopAnchor(newPassLabel, 1.0);
            AnchorPane.setRightAnchor(newPassLabel, 200.0);
            AnchorPane.setRightAnchor(newPassInput, 1.0);
            newPassContainer.setPadding(new Insets(3, 0, 0, 0));

            var wrongPasswordLabel = new Label();
            wrongPasswordLabel.setAlignment(Pos.CENTER);
            wrongPasswordLabel.setPrefWidth(300);
            wrongPasswordLabel.setVisible(false);
            setDanger(wrongPasswordLabel, true);

            var container = new VBox();
            if (adminLoggedInOrCurrentUser) {
                container.getChildren().addAll(newPassContainer, wrongPasswordLabel);
            } else {
                container.getChildren().addAll(oldPassContainer, newPassContainer, wrongPasswordLabel);
            }

            passwordDialog.setTitle("Change password");
            passwordDialog.setHeaderText("Changing password for " + userTabWrapper.selectedObject.get().login.get());
            passwordDialog.getDialogPane().setContent(container);
            passwordDialog.setGraphic(null);

            var positiveButton = passwordDialog.getDialogPane().lookupButton(ButtonType.OK);

            positiveButton.addEventFilter(ActionEvent.ACTION, event -> {
                var oldPass = oldPassInput.getText();
                var newPass = newPassInput.getText();

                if (adminLoggedInOrCurrentUser || loggedInUser.get().checkPassword(oldPass)) {
                    var warn = loggedInUser.get().pass.set(new HashedString(newPass));
                    if (warn == null || warn.isEmpty()) {
                        wrongPasswordLabel.setVisible(false);
                    } else {
                        wrongPasswordLabel.setText(warn);
                        wrongPasswordLabel.setVisible(true);
                        event.consume();
                    }
                } else {
                    wrongPasswordLabel.setText("Wrong password!");
                    wrongPasswordLabel.setVisible(true);
                    event.consume();
                }
            });

            passwordDialog.showAndWait();
        });

        settingsButton.setOnAction(actionEvent -> {
            try {
                Stage settingsWindow = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Entrypoint.class.getResource("settings-view.fxml"));
                settingsWindow.setTitle("Randomshop settings");

                settingsWindow.setScene(new Scene(fxmlLoader.load()));

                settingsWindow.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        initUserTab();
    }

    private void initUserTab() {
        loginButton.setOnAction(actionEvent -> {

            User loggedInUserV = loggedInUser.get();

            if (loggedInUserV == null) {
                var login = userField.getText();
                var password = passwordField.getText();

                for (var user : DaoSingleton.getInstance().userStorage.getObjects()) {
                    if (user.login.get().equals(login)) {
                        if (user.checkPassword(password)) {
                            loggedInUserV = user;
                            loggedInUser.set(loggedInUserV);
                            System.out.println("LOGGED IN AS " + loggedInUserV);
                            break;
                        }
                        invalidUserLabel.setVisible(true);
                        return;
                    }
                }
                if (loggedInUserV == null) {
                    invalidUserLabel.setVisible(true);
                    return;
                }
            } else {
                loggedInUserV = null;
                loggedInUser.set(null);
            }

            invalidUserLabel.setVisible(false);

            boolean isLoggedIn = loggedInUserV != null;

            userField.setDisable(isLoggedIn);
            passwordField.setDisable(isLoggedIn);

            warehousesTab.setDisable(!isLoggedIn);
            productsTab.setDisable(!isLoggedIn);
            usersTab.setDisable(!isLoggedIn);

            loginButton.setText(isLoggedIn ? "Logout" : "Login");
            loginTitle.setText(isLoggedIn ? "Logged in as " + loggedInUserV.login : "Please login");
            setDanger(loginButton, isLoggedIn);
        });
    }

    public boolean notifyCloseRequest() {
        String message = "";
        if (productTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved products, exit?";
        } else if (userTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved users, exit?";
        } else if (warehouseTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved warehouses, exit?";
        }
        if (!message.isEmpty()) {
            Alert closeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            closeDialog.setTitle("Exit");
            closeDialog.setHeaderText(message);
            var result = closeDialog.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        return true;
    }
}
