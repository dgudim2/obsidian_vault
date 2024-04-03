package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.BaseModel;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForeignKeyField<T extends BaseModel> extends Field<Long> {

    protected transient T linkedValue;

    private Supplier<List<T>> linkedObjectsProducer;
    private final Consumer<T> onValueUpdated;

    public ForeignKeyField(@NotNull String name, boolean required, Supplier<List<T>> linkedObjectsProducer, Consumer<T> onValueUpdated) {
        super(name, -1L, required, Long.class, id -> {
            var linkedObjects = linkedObjectsProducer.get();
            if (linkedObjects != null && !linkedObjects.isEmpty()) {
                for (var obj : linkedObjects) {
                    if (obj.id == id) {
                        return "";
                    }
                }
            }
            return id != -1 ? "Invalid link" : "";
        });
        this.linkedObjectsProducer = linkedObjectsProducer;
        this.onValueUpdated = onValueUpdated;
    }

    @Nullable
    public T getLinkedValue() {
        if (linkedValue == null) {
            var linkedObjects = linkedObjectsProducer.get();
            if (linkedObjects != null && !linkedObjects.isEmpty()) {
                for (var obj : linkedObjects) {
                    if (obj.id == get()) {
                        linkedValue = obj;
                        break;
                    }
                }
            }
        }
        return linkedValue;
    }

    public String setLinkedValue(@NotNull T newValue) {
        var warn = set(newValue.id);
        if (warn.isEmpty()) {
            linkedValue = newValue;
        }
        return warn;
    }

    @Override
    @NotNull
    public String set(Long value) {
        var warn = super.set(value);
        if (warn.isEmpty()) {
            linkedValue = null;
        }
        return warn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postRead(@NotNull Field<?> cleanField) {
        linkedObjectsProducer = ((ForeignKeyField<T>) cleanField).linkedObjectsProducer;
        super.postRead(cleanField);
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var linkedObjects = linkedObjectsProducer.get();

        // TODO: auto-refresh
        var comboBox = new ComboBox<>(FXCollections.observableList(linkedObjects));
        Supplier<Boolean> validationCallback = () -> validationCallbackBase.apply(comboBox, set(comboBox.getValue().id));
        T linkedValue = getLinkedValue();
        if (linkedValue != null) {
            comboBox.getSelectionModel().select(linkedValue);
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (validationCallback.get()) {
                onValueUpdated.accept(getLinkedValue());
            }
        });
        return new Pair<>(comboBox, validationCallback);
    }
}
