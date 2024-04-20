package org.kloud.common.fields;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.BaseModel;

import java.util.List;
import java.util.function.*;

/**
 * A field that does not store the object directly, instead only stores its id and resolves the object in runtime
 *
 * @param <T> Object to wrap
 */
public class ForeignKeyField<T extends BaseModel> extends Field<Long> {

    private transient Function<Long, T> linkedObjectProducer;
    private transient Supplier<List<T>> possibleObjectsSupplier;
    private transient BiConsumer<T, T> onValueUpdated;

    public ForeignKeyField(@NotNull String name, boolean required, @NotNull BooleanSupplier hideInUI,
                           @NotNull Function<Long, T> linkedObjectProducer,
                           @NotNull Supplier<List<T>> possibleObjectsSupplier,
                           @NotNull BiConsumer<T, T> onValueUpdated) {
        super(name, -1L, required, Long.class, id -> {
            var linkedObject = linkedObjectProducer.apply(id);
            return (required && linkedObject == null) ? "Invalid link" : "";
        });
        super.hideInUI = hideInUI;
        this.linkedObjectProducer = linkedObjectProducer;
        this.onValueUpdated = onValueUpdated;
        this.possibleObjectsSupplier = possibleObjectsSupplier;
    }

    public ForeignKeyField(@NotNull String name, boolean required,
                           @NotNull Function<Long, T> linkedObjectsProducer,
                           @NotNull Supplier<List<T>> possibleObjectsSupplier,
                           @NotNull BiConsumer<T, T> onValuesUpdated) {
        this(name, required, () -> false, linkedObjectsProducer, possibleObjectsSupplier, onValuesUpdated);
    }

    @Override
    @NotNull
    public Long get() {
        var superValue = super.get();
        return superValue == null ? -1 : superValue;
    }

    @Nullable
    public T getLinkedValue() {
        if (linkedObjectProducer == null) {
            return null;
        }
        return linkedObjectProducer.apply(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postRead(@NotNull Field<?> cleanField) {
        linkedObjectProducer = ((ForeignKeyField<T>) cleanField).linkedObjectProducer;
        onValueUpdated = ((ForeignKeyField<T>) cleanField).onValueUpdated;
        possibleObjectsSupplier = ((ForeignKeyField<T>) cleanField).possibleObjectsSupplier;
        super.postRead(cleanField);
    }

    @Override
    protected Pair<Control, BooleanSupplier> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var linkedObjects = possibleObjectsSupplier.get();

        var comboBox = new ComboBox<>(FXCollections.observableList(linkedObjects));
        BooleanSupplier validationCallback = () -> {
            var value = comboBox.getValue();
            return validationCallbackBase.apply(comboBox, set(value == null ? null : value.id));
        };
        comboBox.getSelectionModel().select(getLinkedValue());
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (validationCallback.getAsBoolean()) {
                onValueUpdated.accept(oldValue, newValue);
            }
        });
        return new Pair<>(comboBox, validationCallback);
    }
}
