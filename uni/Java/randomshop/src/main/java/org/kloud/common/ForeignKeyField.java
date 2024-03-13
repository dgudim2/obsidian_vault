package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ForeignKeyField<T extends BaseModel> extends Field<Long> {

    private Supplier<List<T>> linkedObjectsProducer;

    public ForeignKeyField(@NotNull String name, boolean required, Supplier<List<T>> linkedObjectsProducer) {
        // Ugly workaround with the validator, can't reference linkedObjects before calling super constructor
        super(name, required, Long.class, __ -> "");
        this.linkedObjectsProducer = linkedObjectsProducer;
        validator = id -> {
            var linkedObjects = linkedObjectsProducer.get();
            if (linkedObjects != null && !linkedObjects.isEmpty()) {
                for (var obj : linkedObjects) {
                    if (obj.id == id) {
                        return "";
                    }
                }
            }
            return id != -1 ? "Invalid link" : "";
        };
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

        var comboBox = new ComboBox<T>();
        Supplier<Boolean> validationCallback = () -> validationCallbackBase.apply(comboBox, set(comboBox.getValue().id));
        if (value != null) {
            for (T obj : linkedObjects) {
                if (value == obj.id) {
                    comboBox.getSelectionModel().select(obj);
                    break;
                }
            }
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> validationCallback.get());

        // TODO: autorefresh
        comboBox.setItems(FXCollections.observableList(linkedObjects));

        return new Pair<>(comboBox, validationCallback);
    }
}
