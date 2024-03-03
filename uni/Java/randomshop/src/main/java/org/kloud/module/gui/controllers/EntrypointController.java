package org.kloud.module.gui.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.Breakpoint;
import org.kloud.utils.FileProductsDAO;
import org.kloud.utils.ProductsDAO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static org.kloud.utils.Utils.setDanger;

public class EntrypointController {

    private final ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>(null);

    private final ProductsDAO productsDAO;
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

    public EntrypointController() {
        this.productsDAO = new FileProductsDAO();
    }

    @FXML
    public void initialize() {

        warehousesTab.setDisable(true);
        productsTab.setDisable(true);
        usersTab.setDisable(true);

        initUserTab();
        initProductsTab();
    }

    private void initUserTab() {

        AtomicBoolean loggedIn = new AtomicBoolean(false);

        loginButton.setOnAction(actionEvent -> {

            var user = userField.getText();
            var password = passwordField.getText();
            boolean isLoggedIn = loggedIn.get();

            if(!isLoggedIn && !Objects.equals(user, "admin") && !Objects.equals(password, "admin")) {
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

    private void initProductsTab() {
        productList.getItems().addAll(productsDAO.getProducts());

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(productEditArea.widthProperty());
        pane.prefHeightProperty().bind(productEditArea.heightProperty());
        productEditArea.getChildren().add(pane);
        saveProductButton.setVisible(false);

        productList.getSelectionModel().selectedItemProperty().addListener((observableValue, product, newProduct) -> selectedProduct.set(newProduct));

        deleteProductButton.setDisable(true);
        selectedProduct.addListener((observableValue, oldProduct, newProduct) -> {
            deleteProductButton.setDisable(newProduct == null);
            saveProductButton.setVisible(newProduct != null);
            pane.removeFirstRow();
            if (newProduct != null) {
                pane.addRow(loadItemsForProduct(newProduct, productsDAO));
            }
        });

        deleteProductButton.setOnAction(actionEvent -> {
            Alert productDialog = new Alert(Alert.AlertType.CONFIRMATION);
            productDialog.setTitle("Delete a product");
            productDialog.setHeaderText("Delete '" + selectedProduct.get() + "'?");
            productDialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType != ButtonType.OK) {
                    return;
                }
                var prod = selectedProduct.get();
                productsDAO.removeProduct(prod);
                productList.getItems().remove(prod);
                productList.getSelectionModel().clearSelection();
            });
        });

        addProductButton.setOnAction(actionEvent -> {
            Alert productDialog = new Alert(Alert.AlertType.CONFIRMATION);
            productDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

            final int[] selectedProductIndex = {-1};

            ComboBox<String> productSelector = new ComboBox<>();
            List<String> productNames = new ArrayList<>(Product.PRODUCTS.size());
            for (var product : Product.PRODUCTS) {
                try {
                    productNames.add(String.valueOf(product.getField("NAME").get(null)));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    productNames.add(String.valueOf(product));
                }
            }
            productSelector.setItems(FXCollections.observableList(productNames));
            productSelector.getSelectionModel().selectedIndexProperty().addListener((observableValue, index, newIndex) -> {
                selectedProductIndex[0] = (int) newIndex;
                productDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            });
            productSelector.setPrefWidth(300);

            var container = new HBox();
            container.getChildren().add(productSelector);

            productDialog.setTitle("Add a product");
            productDialog.setHeaderText("Select a product to add");
            productDialog.getDialogPane().setContent(container);
            productDialog.setGraphic(null);

            productDialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType != ButtonType.OK) {
                    return;
                }
                try {
                    productList.getItems().add(Product.PRODUCTS.get(selectedProductIndex[0]).getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private BootstrapRow loadItemsForProduct(@NotNull Product product, @NotNull ProductsDAO productsDAO) {
        BootstrapRow row = new BootstrapRow();
        var fields = product.getFields();
        List<Supplier<Boolean>> fxControlHandlers = new ArrayList<>(fields.size());

        for (var field : fields) {
            var fieldControl = field.getJavaFxControl();
            fxControlHandlers.add(fieldControl.getValue());
            BootstrapColumn column = new BootstrapColumn(fieldControl.getKey());
            column.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
            column.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
            column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
            column.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
            row.addColumn(column);
        }

        System.out.println("Loaded " + fields.size() + " fields for '" + product + "'");

        saveProductButton.setOnAction(actionEvent -> {
            boolean isValid = true;
            for (var fxControlHandler : fxControlHandlers) {
                boolean fieldValid = fxControlHandler.get();
                isValid = isValid && fieldValid;
            }
            if (isValid) {
                productsDAO.addProduct(product);
                productList.refresh();
            }
        });

        return row;
    }

    public boolean notifyCloseRequest() {
        if (productList.getItems().size() != productsDAO.getProducts().size() || !productsDAO.isLatestVersionSaved()) {
            Alert closeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            closeDialog.setTitle("Exit");
            closeDialog.setHeaderText("You have some unsaved products, exit?");
            var result = closeDialog.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        return true;
    }
}
