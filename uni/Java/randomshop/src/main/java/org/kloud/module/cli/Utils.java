package org.kloud.module.cli;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {
    public static Window wrapIntoWindow(@NotNull Component component) {
        var window = new BasicWindow();
        window.setComponent(component);
        window.setHints(List.of(Window.Hint.CENTERED));
        return window;
    }

    public static void showWindow(WindowBasedTextGUI gui, @NotNull Window toAdd) {
        gui.addWindow(toAdd).moveToTop(toAdd);
    }
}
