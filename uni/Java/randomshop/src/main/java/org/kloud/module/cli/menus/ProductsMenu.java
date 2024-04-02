package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;
import org.kloud.daos.BasicDAO;

import static org.kloud.model.product.Product.PRODUCTS;
import static org.kloud.module.cli.Utils.*;

public class ProductsMenu {

    public static void show(@NotNull WindowBasedTextGUI gui, @NotNull BasicDAO<Product> productsDAO) {
        var products = productsDAO.getObjects();

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
