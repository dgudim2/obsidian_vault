package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForeignKeyListField<T extends BaseModel> extends Field<IdList> {

    protected transient List<T> linkedValues;

    private Supplier<List<T>> linkedObjectsProducer;
    private final Consumer<List<T>> onValuesUpdated;

    public ForeignKeyListField(@NotNull String name, boolean required, Supplier<List<T>> linkedObjectsProducer, Consumer<List<T>> onValuesUpdated) {
        super(name, new IdList(), required, IdList.class, id -> {
            var linkedObjects = linkedObjectsProducer.get();
            if (linkedObjects != null && !linkedObjects.isEmpty()) {
                for (var obj : linkedObjects) {
//                    if (obj.id == id) {
//                        return "";
//                    }
                }
            }
            // return id != -1 ? "Invalid link" : "";
            return "";
        });
        this.linkedObjectsProducer = linkedObjectsProducer;
        this.onValuesUpdated = onValuesUpdated;
    }

    @Nullable
    public List<T> getLinkedValues() {
        if (linkedValues == null) {
            var linkedObjects = linkedObjectsProducer.get();
            IdList connectedIds = get();
            List<T> connectedObjects = new ArrayList<>();
            if (linkedObjects != null && !linkedObjects.isEmpty()) {
                for (var obj : linkedObjects) {
                    if (connectedIds.contains(obj.id)) {
                        connectedObjects.add(obj);
                        break;
                    }
                }
            }
            linkedValues = connectedObjects;
        }
        return linkedValues;
    }

    public String setLinkedValues(@NotNull List<T> newValues) {
        var warn = set(new IdList(newValues.stream().map(t -> t.id).toList()));
        if (warn.isEmpty()) {
            linkedValues = newValues;
        }
        return warn;
    }

    @Override
    @NotNull
    public String set(IdList values) {
        var warn = super.set(values);
        if (warn.isEmpty()) {
            linkedValues = null;
        }
        return warn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postRead(@NotNull Field<?> cleanField) {
        linkedObjectsProducer = ((ForeignKeyListField<T>) cleanField).linkedObjectsProducer;
        super.postRead(cleanField);
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var linkedObjects = linkedObjectsProducer.get();

        // TODO: auto-refresh
        var comboBox = new ComboBox<>(FXCollections.observableList(linkedObjects));
        Supplier<Boolean> validationCallback = () -> validationCallbackBase.apply(comboBox, set(new IdList()));
//        T linkedValue = getLinkedValues();
//        if (linkedValue != null) {
//            comboBox.getSelectionModel().select(linkedValue);
//        }
//        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
//            if (validationCallback.get()) {
//                onValueUpdated.accept(getLinkedValues());
//            }
//        });
        return new Pair<>(comboBox, validationCallback);
    }
}
