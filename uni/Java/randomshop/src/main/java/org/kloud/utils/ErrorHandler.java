package org.kloud.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.enums.ErrorResponse;

/**
 * Handles all the errors and displays a ui with choices
 */
public class ErrorHandler {

    public synchronized static ErrorResponse displayException(@NotNull Exception e) {

        e.printStackTrace();
        Logger.fatal(e.getMessage());

        Alert errorDialog = new Alert(Alert.AlertType.ERROR, "", ButtonType.CLOSE, ButtonType.FINISH, ButtonType.YES);
        errorDialog.setHeaderText("An error occurred, continue anyway?");

        ((Button)errorDialog.getDialogPane().lookupButton(ButtonType.FINISH)).setText("Exit app");
        ((Button)errorDialog.getDialogPane().lookupButton(ButtonType.CLOSE)).setText("Ignore");
        ((Button)errorDialog.getDialogPane().lookupButton(ButtonType.YES)).setText("Try to continue");

        Utils.setDanger(errorDialog.getDialogPane().lookupButton(ButtonType.FINISH), true);
        errorDialog.setContentText("Error: " + e.getMessage());
        var res = errorDialog.showAndWait();

        if (res.isEmpty()) {
            return ErrorResponse.IGNORE;
        }

        ButtonType buttonType = res.get();
        if (buttonType.equals(ButtonType.FINISH)) {
            return ErrorResponse.EXIT;
        } else if (buttonType.equals(ButtonType.YES)) {
            return ErrorResponse.ACTION;
        }
        return ErrorResponse.IGNORE;
    }
}
