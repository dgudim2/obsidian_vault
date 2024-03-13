package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EnumField<T extends Enum<T>> extends Field<T> {
    public EnumField(@NotNull String name, boolean required, @NotNull Class<T> klass) {
        super(name, required, klass, __ -> "");
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var comboBox = new ComboBox<T>();
        Supplier<Boolean> validationCallback =
                () -> {
                    var value = comboBox.getValue();
                    return validationCallbackBase.apply(comboBox, set(value));
                };
        if (value != null) {
            comboBox.getSelectionModel().select(value);
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> validationCallback.get());
        comboBox.setItems(FXCollections.observableList(List.of(klass.getEnumConstants())));

        return new Pair<>(comboBox, validationCallback);
    }
}
