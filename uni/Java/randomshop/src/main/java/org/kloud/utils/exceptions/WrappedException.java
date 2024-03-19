package org.kloud.utils.exceptions;

import org.jetbrains.annotations.NotNull;

public class WrappedException {

    public enum Severity {
        INFO, ACTIONABLE, FATAL
    }

    public final Exception e;
    public final Severity severity;

    public WrappedException(@NotNull Exception e, @NotNull Severity severity) {
        this.e = e;
        this.severity = severity;
    }
}
