package org.kloud.module.cli;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.jetbrains.annotations.NotNull;
import org.kloud.module.cli.menus.MainMenu;

import java.io.IOException;

public class Entrypoint {

    public static void launch(@NotNull String[] args) throws IOException {
        // Setup terminal and screen layers
        Screen screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
        screen.startScreen();

        // Create gui and start tui
        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK_BRIGHT));
        MainMenu.show(gui);
    }
}
