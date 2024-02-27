package org.kloud.module.gui.controllers;

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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
    public void initialize() {
        BootstrapPane pane = new BootstrapPane();

        var productsDAO = new FileProductsDAO();
        var prods = productsDAO.readProducts();
        if(prods != null) {
            productList.getItems().addAll(prods);
        }

        pane.prefWidthProperty().bind(productEditArea.widthProperty());
        pane.prefHeightProperty().bind(productEditArea.heightProperty());

        productEditArea.getChildren().add(pane);

        productList.setOnMouseClicked(mouseEvent -> {
            var selectedItem = productList.getSelectionModel().getSelectedItem();
            pane.removeFirstRow();
            pane.addRow(loadItemsForProduct(selectedItem));
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
                if (buttonType == ButtonType.OK) {
                    try {
                        var product = Product.PRODUCTS.get(selectedProductIndex[0]).getDeclaredConstructor().newInstance();
                        productList.getItems().add(product);
                        productsDAO.writeProducts(productList.getItems().stream().toList());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }

    private static BootstrapRow loadItemsForProduct(@NotNull Product product) {
        BootstrapRow row = new BootstrapRow();
        var fields = product.getFields();

        for(var field: fields) {
            BootstrapColumn column = new BootstrapColumn(field.getJavaFxControl());
            column.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
            column.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
            column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
            column.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
            row.addColumn(column);
        }

        return row;
    }
}
