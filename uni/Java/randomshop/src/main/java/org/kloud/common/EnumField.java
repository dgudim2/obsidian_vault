package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EnumField<T extends Enum<T>> extends Field<T> {
    public EnumField(@NotNull String name, boolean required, @NotNull Class<T> klass) {
        super(name, required, klass, __ -> "");
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var comboBox = new ComboBox<String>();
        Supplier<Boolean> validationCallback =
                () -> {
                    var value = comboBox.getValue();
                    return validationCallbackBase.apply(comboBox, set(value == null ? null : Enum.valueOf(klass, value)));
                };
        if (value != null) {
            comboBox.getSelectionModel().select("" + value);
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> validationCallback.get());
        var enumConsts = klass.getEnumConstants();
        List<String> items = new ArrayList<>(enumConsts.length);
        for (T obj : enumConsts) {
            try {
                Method m = klass.getMethod("toString", null);
                items.add(String.valueOf(m.invoke(obj, null)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }
        comboBox.setItems(FXCollections.observableList(items));

        return new Pair<>(comboBox, validationCallback);
    }
}
