package org.kloud.module.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;
import org.kloud.module.gui.TabWrapper;
import org.kloud.utils.FileProductsDAO;
import org.kloud.utils.FileUsersDAO;
import org.kloud.utils.FileWarehousesDAO;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
                new FileProductsDAO(),
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton
        );

        userTabWrapper = new TabWrapper<>(
                "user",
                User.USERS,
                new FileUsersDAO(),
                userEditArea,
                userList,
                deleteUserButton,
                addUserButton,
                saveUserButton
        );

        warehouseTabWrapper = new TabWrapper<>(
                "warehouse",
                List.of(Warehouse.class),
                new FileWarehousesDAO(),
                warehouseEditArea,
                warehousesList,
                deleteWarehouseButton,
                addWarehouseButton,
                saveWarehouseButton
        );

        changeUserPasswordButton.setDisable(true);
        userTabWrapper.selectedObject.addListener((observableValue, oldObject, newObject) -> changeUserPasswordButton.setDisable(newObject == null));
        changeUserPasswordButton.setOnAction(actionEvent -> {
            // TODO: implement
        });

        initUserTab();
    }

    private void initUserTab() {

        AtomicBoolean loggedIn = new AtomicBoolean(false);

        loginButton.setOnAction(actionEvent -> {

            var user = userField.getText();
            var password = passwordField.getText();
            boolean isLoggedIn = loggedIn.get();

            if (!isLoggedIn && !Objects.equals(user, "admin") && !Objects.equals(password, "admin")) {
                invalidUserLabel.setVisible(true);
                return;
            }

            invalidUserLabel.setVisible(false);

            userField.setDisable(!isLoggedIn);
            passwordField.setDisable(!isLoggedIn);

            warehousesTab.setDisable(isLoggedIn);
            productsTab.setDisable(isLoggedIn);
            usersTab.setDisable(isLoggedIn);

            loggedIn.set(!isLoggedIn);

            loginButton.setText(loggedIn.get() ? "Logout" : "Login");
            loginTitle.setText(loggedIn.get() ? "Logged in as admin" : "Please login");
            setDanger(loginButton, loggedIn.get());
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
