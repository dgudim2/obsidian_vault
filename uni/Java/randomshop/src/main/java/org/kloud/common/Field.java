package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Field<T> implements Serializable {

    public final String name;
    @Nullable
    public T value = null;
    public final boolean required;
    private final transient Function<T, String> validator;
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

    @SuppressWarnings("unchecked")
    public Node getJavaFxControl() {

        AnchorPane container = new AnchorPane();
        var label = new Label(name + ": ");

        Control inputField;

        if (klass.equals(String.class)) {
            TextField input = new TextField();
            inputField = input;
            inputField.setOnKeyTyped(keyEvent -> set((T) input.getCharacters().toString()));
        } else if (klass.equals(Integer.class) || klass.equals(Long.class)) {
            inputField = new TextField();
            // TODO: Add validation
        } else if (klass.equals(Float.class) || klass.equals(Double.class)) {
            inputField = new TextField();
            // TODO: Add validation
        } else if (klass.equals(Boolean.class)) {
            inputField = new CheckBox();
        } else if (klass.getSuperclass().equals(Enum.class)) {
            var comboBox = new ComboBox<String>();
            var enumConsts = klass.getEnumConstants();
            List<String> items = new ArrayList<>(enumConsts.length);
            for (Object obj : enumConsts) {
                try {
                    Method m = klass.getMethod("toString", null);
                    items.add(String.valueOf(m.invoke(obj, null)));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
            comboBox.setItems(FXCollections.observableList(items));
            inputField = comboBox;
        } else if (klass.equals(LocalDate.class)) {
            inputField = new DatePicker();
        } else {
            inputField = new Label(klass + " NOT IMPLEMENTED");
        }

        AnchorPane.setRightAnchor(inputField, 0.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        inputField.setPrefWidth(150);
        inputField.setMinWidth(150);
        label.prefHeightProperty().bind(inputField.heightProperty());
        label.setPadding(new Insets(0, 5, 0, 5));
        container.getChildren().addAll(List.of(label, inputField));
        return container;
    }

    @Override
    public String toString() {
        return String.valueOf(value == null ? "uncompleted" : value);
    }
}
