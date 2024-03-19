package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Queue;

public class ErrorLogger {

    private static Queue<Exception> errors = new ArrayDeque<>();

    public synchronized static void addException(@NotNull Exception e) {
        errors.add(e);
    }

    @Nullable
    public synchronized static Exception getLastException() {
        return errors.poll();
    }


}
