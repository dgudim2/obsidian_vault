package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.jetbrains.annotations.NotNull;

import static org.kloud.module.cli.Utils.wrapIntoWindow;

public class MainMenu {
    public static void show(@NotNull WindowBasedTextGUI gui) {
        Panel panel = new Panel();
        Window window = wrapIntoWindow(panel);

        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Manage Users", () -> UserMenu.show(window, gui));
        actionListBox.addItem("2. Manage products", () -> {
            // Code to run when action activated
        });
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(actionListBox);
        panel.addComponent(new Button("Exit", () -> System.exit(0)));

        gui.addWindowAndWait(window);
    }
}
