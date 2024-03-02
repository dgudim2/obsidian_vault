package org.kloud.common;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private transient Function<T, String> validator;
    public final Class<T> klass;

    public Field(@NotNull String name, boolean required, @NotNull Class<T> klass, @NotNull Function<T, String> validator) {
        this.name = name;
        this.klass = klass;
        this.required = required;
        this.validator = validator;
    }

    @SuppressWarnings("unchecked")
    public void postDeserialize(@NotNull Field<?> cleanField) {
        validator = (Function<T, String>) cleanField.validator;
    }

    public T get() {
        return value;
    }

    @Nullable
    private String validate(@Nullable T valueToValidate) {
        if ((valueToValidate == null || String.valueOf(valueToValidate).isEmpty()) && required) {
            return "This field is required";
        }
        var validatorMessage = validator.apply(valueToValidate);
        if (validatorMessage == null || !validatorMessage.isEmpty()) {
            return validatorMessage;
        }
        return null;
    }

    @Nullable
    public String set(T value) {
        var validatorMessage = validate(value);
        if (validatorMessage == null) {
            System.out.println("Set value for " + name + " to " + value);
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
        } else if (klass.equals(Dimensions.class)) {

            Function<String, Pair<AnchorPane, TextField>> getSubInput = s -> {
                var container = new AnchorPane();
                var input = new TextField("0");
                input.setMaxWidth(80);
                AnchorPane.setRightAnchor(input, 0.0);
                AnchorPane.setTopAnchor(input, 1.0);
                AnchorPane.setBottomAnchor(input, 1.0);
                var label = new Label(s);
                label.prefHeightProperty().bind(input.heightProperty());
                AnchorPane.setLeftAnchor(label, 0.0);
                container.getChildren().addAll(label, input);
                return new Pair<>(container, input);
            };

            var dimenContainer = new VBox();

            var w = getSubInput.apply("W: ");
            var h = getSubInput.apply("H: ");
            var d = getSubInput.apply("D: ");

            var wInput = w.getValue();
            var hInput = h.getValue();
            var dInput = d.getValue();

            dimenContainer.getChildren().addAll(w.getKey(), h.getKey(), d.getKey());

            var accordionContent = new TitledPane("Dimensions", dimenContainer);
            accordionContent.fontProperty().setValue(new Font("System", 12));
            inputField = new Accordion(accordionContent);
            validationCallback =
                    () -> {
                        float xValue = Float.parseFloat(wInput.getText());
                        float yValue = Float.parseFloat(hInput.getText());
                        float zValue = Float.parseFloat(dInput.getText());
                        return validationCallbackBase.apply(inputField, set((T) new Dimensions(xValue, yValue, zValue)));
                    };

            if (value != null) {
                wInput.setText("" + ((Dimensions) value).width);
                hInput.setText("" + ((Dimensions) value).height);
                dInput.setText("" + ((Dimensions) value).depth);
            }

            setTextFieldNumberCallback(wInput, validationCallback, false);
            setTextFieldNumberCallback(hInput, validationCallback, false);
            setTextFieldNumberCallback(dInput, validationCallback, false);

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

        final BiFunction<Node, String, Boolean> validationCallbackBase = (@NotNull Node input, @Nullable String validationData) -> {
            System.out.println("Validated " + input + " (" + validationData + ")");
            if (validationData != null) {
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
        return String.valueOf(value == null ? "uncompleted" : value);
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
