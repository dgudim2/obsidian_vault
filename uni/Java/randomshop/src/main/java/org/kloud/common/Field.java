package org.kloud.common;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class Field<T> {

    public final String name;
    @Nullable
    public T value = null;
    public final boolean required;
    private final Function<T, String> validator;
    public final Class<T> klass;

    public Field(@NotNull String name, boolean required, @NotNull Class<T> klass, @NotNull Function<T, String> validator) {
        this.name = name;
        this.klass = klass;
        this.required = required;
        this.validator = validator;
    }

    public T get() {
        return value;
    }

    @Nullable
    public String set(T value) {
        var validatorMessage = validator.apply(value);
        if (!validatorMessage.isEmpty()) {
            return validatorMessage;
        }
        this.value = value;
        return null;
    }

    public Node getFavaFxControl() {

        var hbox = new HBox();
        hbox.setPadding(new Insets(1));
        var label = new Label(name + ": ");

        Control inputField = null;

        if (klass.equals(String.class)) {
            inputField = new TextField();
        }

        if(inputField == null) {
            inputField = new Label(klass + " NOT IMPLEMENTED");
        }

        inputField.prefWidthProperty().bind(hbox.prefWidthProperty());
        label.prefHeightProperty().bind(inputField.heightProperty());
        label.minWidth(70);
        hbox.getChildren().addAll(List.of(label, inputField));
        return hbox;
    }
}
