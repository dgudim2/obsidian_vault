package org.kloud.module.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kloud.common.HashedString;
import org.kloud.common.UserCapability;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.module.gui.TabWrapper;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Logger;
import org.kloud.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.kloud.utils.Utils.setDanger;

public class EntrypointController implements BaseController {

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
    public TabPane tabContainer;
    @FXML
    public Tab userLoginTab;
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
    @FXML
    public AnchorPane root;

    private TabWrapper<Product> productTabWrapper;
    private TabWrapper<User> userTabWrapper;
    private TabWrapper<Warehouse> warehouseTabWrapper;

    @FXML
    public void initialize() {
        var conf = ConfigurationSingleton.getInstance();

        productTabWrapper = new TabWrapper<>(
                "product",
                productsTab,
                Product.PRODUCTS,
                conf.storageBackend.get().getProductStorage(),
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton
        );

        userTabWrapper = new TabWrapper<>(
                "user",
                usersTab,
                User.USERS,
                conf.storageBackend.get().getUserStorage(),
                userEditArea,
                userList,
                deleteUserButton,
                addUserButton,
                saveUserButton
        );

        warehouseTabWrapper = new TabWrapper<>(
                "warehouse",
                warehousesTab,
                List.of(Warehouse.class),
                conf.storageBackend.get().getWarehouseStorage(),
                warehouseEditArea,
                warehousesList,
                deleteWarehouseButton,
                addWarehouseButton,
                saveWarehouseButton
        );

        enableDisableTabs();

        changeUserPasswordButton.setDisable(true);
        userTabWrapper.selectedObject.addListener((observableValue, oldObject, newObject) -> changeUserPasswordButton.setDisable(newObject == null));
        changeUserPasswordButton.setOnAction(actionEvent -> {

            User loggedInUser = ConfigurationSingleton.getLoginController().loggedInUser.get();

            boolean adminLoggedInOrCurrentUser =
                    loggedInUser instanceof Manager m && m.isAdmin.get()
                            || Objects.equals(loggedInUser, userTabWrapper.selectedObject.get());

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

                if (adminLoggedInOrCurrentUser || loggedInUser.checkPassword(oldPass)) {
                    var warn = loggedInUser.pass.set(new HashedString(newPass));
                    if (warn.isEmpty()) {
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
                Utils.loadStage(new Stage(), "settings-view.fxml", "Randomshop settings", stage1 -> {
                    stage1.setResizable(false);
                    stage1.initOwner(root.getScene().getWindow());
                    stage1.initModality(Modality.WINDOW_MODAL);
                });
            } catch (IOException e) {
                Logger.error("Error opening settings: " + e.getMessage());
            }
        });

        initUserLoginTab();
    }

    private void enableDisableTabs() {
        User loggedInUser = ConfigurationSingleton.getLoginController().loggedInUser.get();
        if(loggedInUser == null) {
            warehouseTabWrapper.setEnabled(false);
            productTabWrapper.setEnabled(false);
            userTabWrapper.setEnabled(false);
            return;
        }

        var caps = loggedInUser.getUserCaps();

        warehouseTabWrapper.setEnabled(caps.contains(UserCapability.RW_SELF_WAREHOUSES) || caps.contains(UserCapability.READ_OTHER_WAREHOUSES));
        productTabWrapper.setEnabled(caps.contains(UserCapability.RW_SELF_PRODUCTS) || caps.contains(UserCapability.READ_OTHER_PRODUCTS));
        userTabWrapper.setEnabled(true);
    }

    private void initUserLoginTab() {
        loginButton.setOnAction(actionEvent -> {

            var loginController = ConfigurationSingleton.getLoginController();
            User loggedInUserV = loginController.loggedInUser.get();

            if (loggedInUserV == null) {
                var login = userField.getText();
                var password = passwordField.getText();

                var loginResult = loginController.tryLogin(login, password);

                invalidUserLabel.setVisible(!loginResult);
            } else {
                loginController.logout();
                invalidUserLabel.setVisible(false);
            }
        });
        ConfigurationSingleton.getLoginController().loggedInUser.addListener((observable, oldValue, newValue) -> {
            enableDisableTabs();

            boolean isLoggedIn = newValue != null;

            userField.setDisable(isLoggedIn);
            passwordField.setDisable(isLoggedIn);

            loginButton.setText(isLoggedIn ? "Logout" : "Login");
            loginTitle.setText(isLoggedIn ? "Logged in as " + newValue.login : "Please login");
            setDanger(loginButton, isLoggedIn);
        });
    }

    @Override
    public boolean notifyCloseRequest() {
        String message = "";
        if (productTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved products, exit?";
        } else if (userTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved users, exit?";
        } else if (warehouseTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved warehouses, exit?";
        }
        boolean res = true;
        if (!message.isEmpty()) {
            Alert closeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            closeDialog.setTitle("Exit");
            closeDialog.setHeaderText(message);
            var result = closeDialog.showAndWait();
            res = result.isPresent() && result.get() == ButtonType.OK;
        }
        if(res) {
            try {
                ConfigurationSingleton.getInstance().storageBackend.get().close();
            } catch (Exception e) {
                Logger.error("Exception while closing backend: " + e.getMessage());
            }
        }
        return res;
    }
}
