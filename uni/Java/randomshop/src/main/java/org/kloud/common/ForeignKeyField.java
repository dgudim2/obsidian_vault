package org.kloud.common;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.utils.FileUsersDAO;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ForeignKeyField<T extends BaseModel> extends Field<Long> {

    private List<T> linkedObjects = (List<T>) new FileUsersDAO().getObjects();

    public ForeignKeyField(@NotNull String name, boolean required) {
        // Ugly workaround with the validator, can't reference linkedObjects before calling super constructor
        super(name, required, Long.class, __ -> "");
        validator = id -> {
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

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var comboBox = new ComboBox<T>();
        Supplier<Boolean> validationCallback = () -> validationCallbackBase.apply(comboBox, set(comboBox.getValue().id));
        if (value != null) {
            for(T obj : linkedObjects) {
                if(value == obj.id) {
                    comboBox.getSelectionModel().select(obj);
                    break;
                }
            }
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> validationCallback.get());

        comboBox.setItems(FXCollections.observableList(linkedObjects));

        return new Pair<>(comboBox, validationCallback);
    }

    public void linkToCollection(@NotNull List<T> list) {
        linkedObjects = list;
    }
}
