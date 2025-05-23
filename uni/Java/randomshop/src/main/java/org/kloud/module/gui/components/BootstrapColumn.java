package org.kloud.module.gui.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.Getter;
import org.kloud.utils.Utils;

/**
 * @see <a href="https://github.com/edencoding/javafx-layouts/tree/master/bootstrap-layout-pane/src/main/java/com/edencoding/layouts">Source</a>
 */
public class BootstrapColumn {
    /**
     * -- GETTER --
     *  Get the node in this column
     *
     * @return the content.
     */
    @Getter
    private final Node content;

    int[] columnWidths = new int[]{
            1,  //XS (default)
            -1, //Sm
            -1, //Md
            -1, //Lg
            -1  //XL
    };

    public BootstrapColumn(String content) {
        var label = new Label(content);
        label.setPadding(new Insets(5, 5, 5, 5));
        this.content = label;
        defaultBreakpoints();
    }

    public BootstrapColumn(Node content) {
        this.content = content;
        defaultBreakpoints();
    }

    public void defaultBreakpoints() {
        setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
        setBreakpointColumnWidth(Breakpoint.LARGE, 4);
        setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
    }

    /**
     * Set the column width of the content at the specified breakpoint
     *
     * @param breakPoint the screen size break point being specified
     * @param width      the requested width at this breakpoint (must be between 1 and 12);
     */
    public void setBreakpointColumnWidth(Breakpoint breakPoint, int width) {
        columnWidths[breakPoint.getValue()] = Utils.clamp(width, 1, 12);
    }

    /**
     * Remove a previously-specified column breakpoint width setting
     *
     * @param breakPoint the breakpoint to reset
     */
    public void unsetBreakPoint(Breakpoint breakPoint) {
        columnWidths[breakPoint.getValue()] = -1;
    }

    /**
     * Reset all column width break points, so the default width at all break points is 1.
     */
    public void unsetAllBreakPoints() {
        this.columnWidths = new int[]{
                1,  //XS (default)
                -1, //Sm
                -1, //Md
                -1, //Lg
                -1  //XL
        };
    }

    /**
     * Iterate through breakpoints, beginning at the specified bp, travelling down. Return first valid bp value.
     * If none are valid, return 1
     *
     * @param breakPoint the breakpoint at which to determine the column width
     * @return the requested width at that breakpoint, or based on a lower breakpoint if the specified bp has not been set.
     */
    public int getColumnWidth(Breakpoint breakPoint) {

        //Iterate through breakpoints, beginning at the specified bp, travelling down. Return first valid bp value.
        for (int i = breakPoint.getValue(); i >= 0; i--) {
            if (isValid(columnWidths[i])) return columnWidths[i];
        }

        //If none are valid, return 1
        return 1;
    }

    /**
     * Whether a value is between 1 and 12 (i.e. a valid column width)
     *
     * @param value the value being tested
     * @return whether the value is a valid column width
     */
    private boolean isValid(int value) {
        return value > 0 && value <= 12;
    }
}
