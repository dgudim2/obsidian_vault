package org.kloud.model.enums;

public enum ErrorResponse {
    IGNORE, ACTION, EXIT;

    public void handleDefault() {
        if (this == ErrorResponse.EXIT) {
            System.exit(1);
        }
    }
}
