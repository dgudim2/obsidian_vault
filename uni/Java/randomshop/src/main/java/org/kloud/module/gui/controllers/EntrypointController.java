package org.kloud.module.gui.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

public class EntrypointController {

    private final ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>(null);

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

    private void saveProducts(@NotNull ProductsDAO productsDAO) {
        productsDAO.writeProducts(productList.getItems().stream().filter(product -> product.getInvalidField() == null).toList());
    }

    @FXML
    public void initialize() {

        var productsDAO = new FileProductsDAO();
        var prods = productsDAO.readProducts();
        if(prods != null) {
            productList.getItems().addAll(prods);
        }

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(productEditArea.widthProperty());
        pane.prefHeightProperty().bind(productEditArea.heightProperty());
        productEditArea.getChildren().add(pane);
        saveProductButton.setVisible(false);

        productList.setOnMouseClicked(mouseEvent -> {
            var selectedProduct = productList.getSelectionModel().getSelectedItem();
            // Clear selection when the same item is selected / no item is selected
            if (selectedProduct != null && Objects.equals(this.selectedProduct.get(), selectedProduct)) {
                productList.getSelectionModel().clearSelection();
                selectedProduct = null;
            }
            this.selectedProduct.set(selectedProduct);
        });

        productList.getItems().addListener((ListChangeListener<Product>) change -> saveProducts(productsDAO));

        deleteProductButton.setDisable(true);
        selectedProduct.addListener((observableValue, oldProduct, newProduct) -> {
            deleteProductButton.setDisable(newProduct == null);
            saveProductButton.setVisible(newProduct != null);
            pane.removeFirstRow();
            if (newProduct != null) {
                pane.addRow(loadItemsForProduct(newProduct));
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
                productList.getItems().remove(selectedProduct.get());
                productList.getOnMouseClicked().handle(null);
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
            productSelector.setOnAction(actionEvent1 -> {
                selectedProductIndex[0] = productSelector.getSelectionModel().getSelectedIndex();
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

    private BootstrapRow loadItemsForProduct(@NotNull Product product) {
        BootstrapRow row = new BootstrapRow();
        var fields = product.getFields();
        List<Control> fxControls = new ArrayList<>(fields.size());

        for(var field: fields) {
            var fieldControl = field.getJavaFxControl();
            fxControls.add((Control) fieldControl.lookup("input"));
            BootstrapColumn column = new BootstrapColumn(fieldControl);
            column.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
            column.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
            column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
            column.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
            row.addColumn(column);
        }

        System.out.println("Loaded " + fields.size() + " fields for '" + product + "'");

        saveProductButton.setOnAction(actionEvent -> {
            for(var field: fields) {
                var validationData = field.validate();
                if(validationData != null) {

                }
            }
        });

        return row;
    }
}
