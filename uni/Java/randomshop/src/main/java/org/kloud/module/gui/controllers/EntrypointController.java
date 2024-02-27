package org.kloud.module.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kloud.model.product.Product;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.Breakpoint;

import java.util.ArrayList;
import java.util.List;

public class EntrypointController {
    @FXML
    public ListView<Product>productList;
    @FXML
    public Pane productEditArea;
    @FXML
    public Button deleteProductButton;
    @FXML
    public Button addProductButton;

    @FXML
    public void initialize() {
        BootstrapPane pane = new BootstrapPane();
        pane.addRow(makeBootstrapRow());

        pane.prefWidthProperty().bind(productEditArea.widthProperty());
        pane.prefHeightProperty().bind(productEditArea.heightProperty());

        productEditArea.getChildren().add(pane);

        addProductButton.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            ComboBox<String> productSelector = new ComboBox<>();
            List<String> productNames = new ArrayList<>(Product.PRODUCTS.size());
            for(var product: Product.PRODUCTS) {
                try {
                    productNames.add(String.valueOf(product.getField("NAME").get(null)));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    productNames.add(String.valueOf(product));
                }
            }
            productSelector.setItems(FXCollections.observableList(productNames));

            var container = new HBox();
            container.getChildren().add(productSelector);
            productSelector.prefWidthProperty().bind(container.prefWidthProperty());
            container.setSpacing(5);

            alert.setTitle("Add a product");
            alert.setHeaderText("Select a product to add");
            alert.getDialogPane().setContent(container);

            alert.showAndWait();
        });
    }

    private static BootstrapRow makeBootstrapRow() {
        BootstrapRow row = new BootstrapRow();

        BootstrapColumn column0 = new BootstrapColumn(createColoredPane());
        column0.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
        column0.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
        column0.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        column0.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
        BootstrapColumn column1 = new BootstrapColumn(createColoredPane());
        column1.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
        column1.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
        column1.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        column1.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
        BootstrapColumn column2 = new BootstrapColumn(createColoredPane());
        column2.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
        column2.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
        column2.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        column2.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
        BootstrapColumn column3 = new BootstrapColumn(createColoredPane());
        column3.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
        column3.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
        column3.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        column3.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);

        row.addColumn(column0);
        row.addColumn(column1);
        row.addColumn(column2);
        row.addColumn(column3);

        return row;
    }

    private static HBox createColoredPane() {
        var hbox = new HBox();
        hbox.setPadding(new Insets(1));
        var label = new Label("Test: ");
        var text = new TextField();
        text.prefWidthProperty().bind(hbox.prefWidthProperty());
        label.prefHeightProperty().bind(text.heightProperty());
        label.minWidth(70);
        hbox.getChildren().addAll(List.of(label, text));
        return hbox;
    }
}
