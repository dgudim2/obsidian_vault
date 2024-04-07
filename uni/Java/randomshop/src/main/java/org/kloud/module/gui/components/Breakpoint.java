package org.kloud.module.gui.components;

/**
 * @see <a href="https://github.com/edencoding/javafx-layouts/tree/master/bootstrap-layout-pane/src/main/java/com/edencoding/layouts">Source</a>
 */
public enum Breakpoint {
    XSMALL(0),
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    XLARGE(4);

    private int value;

    Breakpoint(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
