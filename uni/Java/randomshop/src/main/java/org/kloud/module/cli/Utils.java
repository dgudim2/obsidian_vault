package org.kloud.module.cli;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {
    public static Window wrapIntoWindow(@NotNull Component component) {
        var window = new BasicWindow();
        window.setComponent(component);
        window.setHints(List.of(Window.Hint.CENTERED));
        return window;
    }

    public static Panel newVerticalPanel() {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        return panel;
    }

    public static void setupWithExitButton(@NotNull String text, @NotNull Panel panel, @NotNull Runnable action, @NotNull Component... components) {
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        for(var component: components) {
            panel.addComponent(component);
        }
        panel.addComponent(new Button(text, action));
    }
}
