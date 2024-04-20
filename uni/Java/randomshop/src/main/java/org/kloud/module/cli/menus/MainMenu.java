package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.jetbrains.annotations.NotNull;
import org.kloud.utils.Conf;

import static org.kloud.module.cli.Utils.*;

public class MainMenu {
    public static void show(@NotNull WindowBasedTextGUI gui) {
        Panel panel = newVerticalPanel();

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Manage Users", () -> UserMenu.show(gui, Conf.getStorage().getUserStorage()));
        actionListBox.addItem("2. Manage products", () -> ProductsMenu.show(gui, Conf.getStorage().getProductStorage()));
        setupWithExitButton("Exit", panel, () -> System.exit(0), actionListBox);

        gui.addWindowAndWait(wrapIntoWindow(panel));
    }
}
