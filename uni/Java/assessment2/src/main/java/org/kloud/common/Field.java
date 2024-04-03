package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.ColumnDescriptor;
import org.kloud.utils.Logger;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.kloud.utils.Utils.setDanger;

public class Field<T extends Serializable> implements Serializable {

    public final String name;
    @Nullable
    protected T value;
    public final boolean required;
    protected transient Function<T, @NotNull String> validator;
    public final Class<T> klass;

    private ColumnDescriptor<T> columnDescriptor;

    public Field(@NotNull String name, @Nullable T defaultValue, boolean required, @NotNull Class<T> klass, @NotNull Function<T, @NotNull String> validator) {
        this.name = name;
        value = defaultValue;
        this.klass = klass;
        this.required = required;
        this.validator = validator;
    }

    public Field(@NotNull String name, boolean required, @NotNull Class<T> klass, @NotNull Function<T, @NotNull String> validator) {
        this(name, null, required, klass, validator);
    }

    @SuppressWarnings("unchecked")
    public void postRead(@NotNull Field<?> cleanField) {
        Logger.debug("postRead for " + name + " (" + value + ")");
        validator = (Function<T, String>) cleanField.validator;
    }

    @NotNull
    public ColumnDescriptor<T> getColumnDescriptor() {
        if(columnDescriptor == null) {
            columnDescriptor = new ColumnDescriptor<>(this);
        }
        return columnDescriptor;
    }

    public T get() {
        return value;
    }

    @NotNull
    private String validate(@Nullable T valueToValidate) {
        if ((valueToValidate == null || String.valueOf(valueToValidate).isEmpty()) && required) {
            return "This field is required";
        }
        return validator.apply(valueToValidate);
    }

    @NotNull
    public String set(T value) {
        var validatorMessage = validate(value);
        if (validatorMessage.isEmpty()) {
            Logger.info("Set value for " + name + " to " + value);
            this.value = value;
        }
        return validatorMessage;
    }

    private void setTextFieldNumberCallback(@NotNull TextField inputField, @NotNull Supplier<Boolean> validationCallback, boolean isWhole) {
        String matchRegex = isWhole ? "\\d*" : "\\d*|\\.";
        String replaceRegex = isWhole ? "\\D" : "[^0-9.]";
        inputField.textProperty().addListener((observableValue, prevValue, newValue) -> {
            if (!newValue.matches(matchRegex)) {
                inputField.setText(newValue.replaceAll(replaceRegex, ""));
            } else if (newValue.isEmpty()) {
                inputField.setText("0");
            }
            validationCallback.get();
        });
    }

    @SuppressWarnings("unchecked")
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {
        Control inputField;
        Supplier<Boolean> validationCallback;

        if (klass.equals(Integer.class) || klass.equals(Long.class) || klass.equals(Float.class) || klass.equals(Double.class) || klass.equals(String.class)) {
            inputField = new TextField(klass.equals(String.class) ? "" : "0");
            if (value != null) {
                ((TextField) inputField).setText("" + value);
            }

            final Function<String, T> converter = str -> {
                if (klass.equals(String.class)) {
                    return (T) str;
                }
                try {
                    return (T) klass.getMethod("valueOf", String.class).invoke(null, str);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            };
            validationCallback =
                    () -> validationCallbackBase.apply(inputField, set(converter.apply(((TextField) inputField).getText())));

            if (klass.equals(String.class)) {
                ((TextField) inputField).textProperty().addListener((observableValue, prevValue, newValue) -> validationCallback.get());
            } else {
                setTextFieldNumberCallback((TextField) inputField, validationCallback, klass.equals(Integer.class) || klass.equals(Long.class));
            }
        } else if (klass.equals(Boolean.class)) {
            inputField = new CheckBox(Boolean.toString(Objects.equals(Boolean.TRUE, value)));
            ((CheckBox) inputField).selectedProperty().setValue(Objects.equals(Boolean.TRUE, value));
            validationCallback = () -> true;
            ((CheckBox) inputField).selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                ((CheckBox) inputField).setText(Boolean.toString(Objects.equals(Boolean.TRUE, newValue)));
                set((T) newValue);
            });
        } else if (klass.equals(LocalDate.class)) {
            inputField = new DatePicker((LocalDate) value);
            validationCallback =
                    () -> {
                        try {
                            String rawData = ((DatePicker) inputField).getEditor().getText();
                            return validationCallbackBase.apply(inputField, set((T) ((DatePicker) inputField).getConverter().fromString(rawData)));
                        } catch (Exception e) {
                            return validationCallbackBase.apply(inputField, "Wrong format");
                        }
                    };
            ((DatePicker) inputField).getEditor().textProperty().addListener((observableValue, textField, t1) -> validationCallback.get());
            ((DatePicker) inputField).valueProperty().addListener((observableValue, localDate, t1) -> validationCallback.get());
        } else if (klass.equals(Color.class)) {
            Color col = (Color) value;
            inputField = col == null ? new ColorPicker() : new ColorPicker(new javafx.scene.paint.Color(
                    col.getRed() / 255.0,
                    col.getGreen() / 255.0,
                    col.getBlue() / 255.0,
                    col.getAlpha() / 255.0
            ));
            validationCallback =
                    () -> {
                        var fxColor = ((ColorPicker) inputField).getValue();
                        return validationCallbackBase.apply(inputField, set((T) new Color(
                                (int) (fxColor.getRed() * 255),
                                (int) (fxColor.getGreen() * 255),
                                (int) (fxColor.getBlue() * 255),
                                (int) (fxColor.getOpacity() * 255)
                        )));
                    };
        } else if (klass.isEnum()) {
            var comboBox = new ComboBox<>(FXCollections.observableList(List.of(klass.getEnumConstants())));
            inputField = comboBox;
            validationCallback =
                    () -> validationCallbackBase.apply(comboBox, set(comboBox.getValue()));
            if (value != null) {
                comboBox.getSelectionModel().select(value);
            } else {
                comboBox.getSelectionModel().selectFirst();
            }
            comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> validationCallback.get());
        } else {
            inputField = new Label(klass + " NOT IMPLEMENTED");
            validationCallback = null;
        }

        return new Pair<>(inputField, validationCallback);
    }

    public Pair<Node, Supplier<Boolean>> getJavaFxControl() {

        var label = new Label(name + ": " + (required ? "*" : ""));
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setBottomAnchor(label, 0.0);

        var errorLabel = new Label("");
        setDanger(errorLabel, true);
        errorLabel.setVisible(false);
        errorLabel.setFont(new Font("System", 11));
        AnchorPane.setLeftAnchor(errorLabel, 0.0);
        AnchorPane.setBottomAnchor(errorLabel, 0.0);

        var labelContainer = new AnchorPane();
        labelContainer.getChildren().addAll(label, errorLabel);
        labelContainer.setPadding(new Insets(0, 5, 0, 5));

        final BiFunction<Node, String, Boolean> validationCallbackBase = (@NotNull Node input, @NotNull String validationData) -> {
            Logger.debug("Validated " + input + " (" + validationData + ")");
            if (!validationData.isEmpty()) {
                setDanger(input, true);
                errorLabel.setText(validationData);
                errorLabel.setVisible(true);
                // Move main text up 13 units to make room for error text
                AnchorPane.setBottomAnchor(label, 13.0);
                return false;
            } else {
                setDanger(input, false);
                errorLabel.setVisible(false);
                AnchorPane.setBottomAnchor(label, 0.0);
                return true;
            }
        };

        var controlBase = getJavaFxControlBase(validationCallbackBase);
        var inputField = controlBase.getKey();

        AnchorPane.setRightAnchor(inputField, 0.0);
        inputField.setPrefWidth(150);
        inputField.setMinWidth(150);
        labelContainer.prefHeightProperty().bind(inputField.heightProperty());

        AnchorPane container = new AnchorPane();
        container.setPadding(new Insets(0, 0, 5, 0));
        container.getChildren().addAll(List.of(labelContainer, inputField));
        return new Pair<>(container, controlBase.getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(value == null ? "-" : value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field<?> field)) return false;
        return required == field.required && Objects.equals(name, field.name) && Objects.equals(value, field.value) && Objects.equals(klass, field.klass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, required, klass);
    }
}
