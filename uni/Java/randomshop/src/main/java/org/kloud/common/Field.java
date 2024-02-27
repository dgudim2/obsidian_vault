package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.kloud.utils.Utils.setDanger;

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
    private String validate(@Nullable T valueToValidate) {
        if((valueToValidate == null || String.valueOf(valueToValidate).isEmpty()) && required) {
            return "This field is required";
        }
        var validatorMessage = validator.apply(valueToValidate);
        if (validatorMessage == null || !validatorMessage.isEmpty()) {
            return validatorMessage;
        }
        return null;
    }

    @Nullable
    public String validate() {
        return validate(value);
    }

    @Nullable
    public String set(T value) {
        var validatorMessage = validate(value);
        if (validatorMessage != null) {
            this.value = value;
        }
        return validatorMessage;
    }

    @SuppressWarnings("unchecked")
    public Node getJavaFxControl() {

        var label = new Label(name + ": " + (required ? "*" : ""));
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setBottomAnchor(label, 0.0);

        var errorLabel = new Label("");
        setDanger(errorLabel, true);
        errorLabel.setVisible(false);
        AnchorPane.setLeftAnchor(errorLabel, 0.0);
        AnchorPane.setBottomAnchor(errorLabel, 0.0);

        var labelContainer = new AnchorPane();
        labelContainer.getChildren().addAll(label, errorLabel);
        labelContainer.setPadding(new Insets(0, 5, 0, 5));

        Control inputField;

        if (klass.equals(String.class)) {
            TextField input = new TextField();
            inputField = input;
            inputField.setOnKeyTyped(keyEvent -> {
                var validationData = set((T) input.getCharacters().toString());
                if(validationData != null) {
                    setDanger(input, true);
                    errorLabel.setText(validationData);
                    errorLabel.setVisible(true);
                    // Move main text up 13 units to make room for error text
                    AnchorPane.setBottomAnchor(label, 13.0);
                } else {
                    setDanger(input, false);
                    errorLabel.setVisible(false);
                    AnchorPane.setBottomAnchor(label, 0.0);
                }
            });
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
        } else if (klass.equals(Dimensions.class)) {
            inputField = new Button("Set dimensions");
        } else if (klass.equals(Color.class)) {
            Color col = (Color) value;
            inputField = col == null ? new ColorPicker() : new ColorPicker(col);
        } else {
            inputField = new Label(klass + " NOT IMPLEMENTED");
        }

        AnchorPane.setRightAnchor(inputField, 0.0);
        inputField.setPrefWidth(150);
        inputField.setMinWidth(150);
        inputField.setId("input");
        labelContainer.prefHeightProperty().bind(inputField.heightProperty());

        AnchorPane container = new AnchorPane();
        container.setPadding(new Insets(0, 0, 5, 0));
        container.getChildren().addAll(List.of(labelContainer, inputField));
        return container;
    }

    @Override
    public String toString() {
        return String.valueOf(value == null ? "uncompleted" : value);
    }
}
