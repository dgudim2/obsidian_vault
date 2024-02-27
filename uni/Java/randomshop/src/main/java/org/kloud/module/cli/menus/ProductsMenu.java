package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import org.jetbrains.annotations.NotNull;
import org.kloud.utils.ProductsDAO;

import static org.kloud.model.product.Product.PRODUCTS;
import static org.kloud.module.cli.Utils.*;

public class ProductsMenu {

    public static void show(@NotNull WindowBasedTextGUI gui, @NotNull ProductsDAO productsDAO) {
        var products = productsDAO.readProducts();
        if (products == null) {
            new MessageDialogBuilder().setTitle("An error occurred!").setText("Could not read products!").build().showDialog(gui);
            return;
        }

        Panel panel = newVerticalPanel();
        Window window = wrapIntoWindow(panel);

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Add product", () -> addProductSelect(gui));
        actionListBox.addItem("2. Update product", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("3. Read product", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("4. Delete product", () -> {});
        actionListBox.addItem("5. Read all products", () -> {});

        setupWithExitButton("Back", panel, () -> gui.removeWindow(window), actionListBox);

        gui.addWindow(window);
    }

    private static void addProductSelect(@NotNull WindowBasedTextGUI gui) {
        Panel panel = newVerticalPanel();
        Window window = wrapIntoWindow(panel);

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));

        setupWithExitButton("Back", panel, () -> gui.removeWindow(window), new ComboBox<>(PRODUCTS));

        gui.addWindow(window);
    }

}
