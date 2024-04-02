package org.kloud.model.enums;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.utils.ErrorHandler;

import java.util.concurrent.Callable;

public enum ErrorResponse {
    IGNORE, ACTION, EXIT;

    public void handleDefault() {
        if (this == ErrorResponse.EXIT) {
            System.exit(1);
        }
    }

    @Nullable
    public <T> T handleWithAction(@NotNull Callable<T> action) {
        if (this == ErrorResponse.EXIT) {
            System.exit(1);
        } else if (this == ErrorResponse.ACTION) {
            try {
                return action.call();
            } catch (Exception e) {
                // NOTE: This is probably not how it should be done, but whatever, retry the retry I guess
                return ErrorHandler.displayException(new Exception("Exception while retrying: " + e.getMessage())).handleWithAction(action);
            }
        }
        return null;
    }
}
