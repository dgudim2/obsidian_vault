package org.kloud.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic logger
 */
public class Logger {
    public enum Loglevel {
        DEBUG, INFO, SUCCESS, WARN, ERROR, FATAL
    }

    private static long lastLogTime;
    public static final StringProperty lastMessage = new SimpleStringProperty("");

    private static final Map<Loglevel, String> colorMap = new HashMap<>();

    static {
        colorMap.put(Loglevel.DEBUG, "\033[38;5;242m[DEBUG]: \033[0m");
        colorMap.put(Loglevel.INFO, "\033[1;34m[INFO]: \033[0m");
        colorMap.put(Loglevel.SUCCESS, "\033[0;32m[SUCCESS]: \033[0m");
        colorMap.put(Loglevel.WARN, "\033[1;33m[WARN]: \033[0m");
        colorMap.put(Loglevel.ERROR, "\033[0;31m[ERROR]: \033[0m");
        colorMap.put(Loglevel.FATAL, "\033[1;31m[FATAL]: \033[0m");
    }

    private static synchronized void log(@NotNull Loglevel loglevel, String message) {
        if (loglevel.ordinal() >= ConfigurationSingleton.getInstance().targetLogLevel.get().ordinal()) {
            var prefix = colorMap.get(loglevel);
            if(System.currentTimeMillis() - lastLogTime < 1000) {
                lastMessage.setValue(lastMessage.get() + "\n" + message);
            } else {
                lastMessage.setValue(message);
            }
            System.out.println(prefix + message);
            lastLogTime = System.currentTimeMillis();
        }
    }

    public static void debug(String message) {
        log(Loglevel.DEBUG, message);
    }

    public static void info(String message) {
        log(Loglevel.INFO, message);
    }

    public static void success(String message) {
        log(Loglevel.SUCCESS, message);
    }

    public static void warn(String message) {
        log(Loglevel.WARN, message);
    }

    public static void error(String message) {
        log(Loglevel.ERROR, message);
    }

    public static void fatal(String message) {
        log(Loglevel.FATAL, message);
    }

}
