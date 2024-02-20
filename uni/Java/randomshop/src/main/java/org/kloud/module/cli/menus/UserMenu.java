package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.jetbrains.annotations.NotNull;
import org.kloud.utils.UserDAO;

import static org.kloud.module.cli.Utils.showWindow;
import static org.kloud.module.cli.Utils.wrapIntoWindow;

public class UserMenu {
    public static void show(@NotNull WindowBasedTextGUI gui, UserDAO userDAO) {
        Panel panel = new Panel();
        Window window = wrapIntoWindow(panel);

        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Add user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("2. Update user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("3. Read user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("4. Delete user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("5. Read all users", () -> {
            // Code to run when action activated
        });
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(actionListBox);
        panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));

        showWindow(gui, window);
    }
}
