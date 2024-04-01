package org.kloud.module.cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.user.User;
import org.kloud.daos.BasicDAO;

import java.util.List;
import java.util.regex.Pattern;

import static org.kloud.module.cli.Utils.*;

public class UserMenu {


    public static void show(@NotNull WindowBasedTextGUI gui, @NotNull BasicDAO<User> userDAO) {
        var users = userDAO.getObjects();

        Panel panel = newVerticalPanel();
        Window window = wrapIntoWindow(panel);

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Add user", () -> addUser(gui, userDAO, users));
        actionListBox.addItem("2. Update user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("3. Read user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("4. Delete user", () -> deleteUser(gui, userDAO, users));
        actionListBox.addItem("5. Read all users", () -> showAllUsers(gui, users));

        setupWithExitButton("Back", panel, () -> gui.removeWindow(window), actionListBox);

        gui.addWindow(window);
    }

    private static void trySaveUsers(@NotNull WindowBasedTextGUI gui, @NotNull Window calledFrom, boolean saveSuccess) {
        if (saveSuccess) {
            new MessageDialogBuilder().setTitle("An error occurred!").setText("Failed saving users!").build().showDialog(gui);
        } else {
            gui.removeWindow(calledFrom);
        }
    }

    private static void addUser(@NotNull WindowBasedTextGUI gui, @NotNull BasicDAO<User> userDAO, @NotNull List<User> users) {
        Panel panel = new Panel();
        Window window = wrapIntoWindow(panel);

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Add a user").setLayoutData(BorderLayout.Location.CENTER));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Name: "));
        var nameInput = new TextBox().setValidationPattern(Pattern.compile("\\p{L}*")).addTo(panel);
        panel.addComponent(new Label("Surname: "));
        var surnameInput = new TextBox().setValidationPattern(Pattern.compile("\\p{L}*")).addTo(panel);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Button("Add", () -> {
//            var user = new User();
//            user.name.set(nameInput.getText());
//            user.surname.set(surnameInput.getText());
//            trySaveUsers(gui, window, userDAO.addObject(user));
        }));
        panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

        gui.addWindow(window);
    }

    private static void deleteUser(@NotNull WindowBasedTextGUI gui, @NotNull BasicDAO<User> userDAO, @NotNull List<User> users) {
        var panel = newVerticalPanel();
        var window = wrapIntoWindow(panel);

        panel.addComponent(new Label("Here is a list of users").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        for (var user : users) {
            actionListBox.addItem(user.toString(), () -> {
                var result = new MessageDialogBuilder()
                        .setTitle("Confirm deletion")
                        .setText("Delete " + user + "?")
                        .addButton(MessageDialogButton.No)
                        .addButton(MessageDialogButton.Yes)
                        .build().showDialog(gui);
                if (result == MessageDialogButton.Yes) {
                    trySaveUsers(gui, window, users.remove(user));
                    deleteUser(gui, userDAO, users);
                }
            });
        }

        setupWithExitButton("Back", panel, () -> gui.removeWindow(window), actionListBox);

        gui.addWindow(window);
    }

    private static void showAllUsers(@NotNull WindowBasedTextGUI gui, @NotNull List<User> users) {
        Panel panel = newVerticalPanel();
        Window window = wrapIntoWindow(panel);

        panel.addComponent(new Label("Here is a list of users").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        for (var user : users) {
            actionListBox.addItem(user.toString(), () -> {
            });
        }

        setupWithExitButton("Back", panel, () -> gui.removeWindow(window), actionListBox);

        gui.addWindow(window);
    }
}
