package org.kloud.common.fields;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.kloud.utils.Utils;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * A {@link Field} holding a float but with a {@link Slider} instead of a {@link javafx.scene.control.TextField TextField}
 */
public class RatingField extends Field<Double> {

    public RatingField(@NotNull String name) {
        super(name, true, Double.class, val -> Utils.testBounds(val, 0, 10));
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {
        var inputField = new Slider(0, 10, value == null ? 0 : value);

        Supplier<Boolean> validationCallback =
                () -> validationCallbackBase.apply(inputField, set(inputField.getValue()));

        inputField.setShowTickLabels(true);
        inputField.setShowTickMarks(true);
        inputField.setSnapToTicks(true);
        // Major step of 1
        inputField.setMajorTickUnit(1);
        // One step between 2 ticks dividing the range into 0.5 segments
        inputField.setMinorTickCount(1);

        inputField.valueProperty().addListener((observableValue, prevValue, newValue) -> validationCallback.get());

        return new Pair<>(inputField, validationCallback);
    }
}
