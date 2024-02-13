package org.kloud.module.cli;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class Entrypoint {
    @NotNull
    private WindowBasedTextGUI gui;
    @NotNull
    private BasicWindow mainWindow;


    private Entrypoint() throws IOException {
        // Setup terminal and screen layers
        Screen screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
        screen.startScreen();

        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        panel.addComponent(new Label("Choose what to do").setLayoutData(BorderLayout.Location.CENTER));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(30, 10));
        actionListBox.addItem("1. Add user", () -> {
            var win = Test();
            gui.addWindow(win);
            gui.moveToTop(win);
            // Code to run when action activated
        });
        actionListBox.addItem("2. Delete user", () -> {
            // Code to run when action activated
        });
        actionListBox.addItem("3. Update user", () -> {
            // Code to run when action activated
        });
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(actionListBox);
        panel.addComponent(new Button("Exit", () -> System.exit(0)));

        // Create window to hold the panel
        mainWindow = new BasicWindow();
        mainWindow.setComponent(panel);
        mainWindow.setHints(List.of(Window.Hint.CENTERED));

        // Create gui and start tui
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK_BRIGHT));
        gui.addWindowAndWait(mainWindow);
    }

    public BasicWindow Test() {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        BasicWindow window = new BasicWindow();
        panel.addComponent(new Button("Exit", () -> gui.removeWindow(window)));

        window.setComponent(panel);
        window.setHints(List.of(Window.Hint.CENTERED));

        return window;
    }

    public static void launch() throws IOException {
        new Entrypoint();
    }

}
