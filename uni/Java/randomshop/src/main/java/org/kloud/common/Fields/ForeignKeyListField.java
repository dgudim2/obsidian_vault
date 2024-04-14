package org.kloud.common.Fields;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.datatypes.IdList;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Same as {@link ForeignKeyField}, but stores a list of objects
 * @param <T>
 */
public class ForeignKeyListField<T extends BaseModel> extends Field<IdList> {

    public final boolean virtual;
    private Function<List<Long>, List<T>> linkedObjectsProducer;
    private Supplier<List<T>> possibleObjectsSupplier;
    private BiConsumer<List<T>, List<T>> onValuesUpdated;

    public ForeignKeyListField(@NotNull String name, boolean required, boolean virtual, boolean hideInUI,
                               @NotNull Function<List<Long>, List<T>> linkedObjectsProducer,
                               @NotNull Supplier<List<T>> possibleObjectsSupplier,
                               @NotNull BiConsumer<List<T>, List<T>> onValuesUpdated) {
        super(name, new IdList(), required, IdList.class, ids -> {
            var linkedObjects = linkedObjectsProducer.apply(ids.backingList);
            return (required && linkedObjects.isEmpty()) ? "At least one value is required" : "";
        });
        this.virtual = virtual;
        this.hideInUI = hideInUI;
        this.linkedObjectsProducer = linkedObjectsProducer;
        this.onValuesUpdated = onValuesUpdated;
        this.possibleObjectsSupplier = possibleObjectsSupplier;
    }

    public ForeignKeyListField(@NotNull String name,
                               @NotNull Function<List<Long>, List<T>> linkedObjectsProducer,
                               @NotNull Supplier<List<T>> possibleObjectsSupplier,
                               @NotNull BiConsumer<List<T>, List<T>> onValuesUpdated) {
        this(name, false, false, false, linkedObjectsProducer, possibleObjectsSupplier, onValuesUpdated);
    }

    @Override
    public boolean hasChanges() {
        if (virtual) {
            // Virtual fields don't need to be saved, all they do is update some other non-virtual field via 'onValuesUpdated' callback
            return false;
        }
        return super.hasChanges();
    }

    @Override
    @NotNull
    public IdList get() {
        var superValue = super.get();
        return superValue == null ? new IdList() : superValue;
    }

    @NotNull
    public List<T> getLinkedValues() {
        if (value == null) {
            return List.of();
        }
        return linkedObjectsProducer.apply(value.backingList);
    }

    @NotNull
    public String setLinkedValues(@NotNull List<T> newValues) {
        return set(new IdList(newValues.stream().map(t -> t.id).toList()));
    }

    public String addLinkedValue(@NotNull T newValue) {
        var newValues = value == null ? new IdList() : new IdList(value);
        newValues.add(newValue.id);
        return set(newValues);
    }

    public String removeLinkedValue(@NotNull T newValue) {
        var newValues = value == null ? new IdList() : new IdList(value);
        newValues.remove(newValue.id);
        return set(newValues);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postRead(@NotNull Field<?> cleanField) {
        linkedObjectsProducer = ((ForeignKeyListField<T>) cleanField).linkedObjectsProducer;
        possibleObjectsSupplier = ((ForeignKeyListField<T>) cleanField).possibleObjectsSupplier;
        onValuesUpdated = ((ForeignKeyListField<T>) cleanField).onValuesUpdated;
        super.postRead(cleanField);
    }

    @Override
    protected Pair<Control, Supplier<Boolean>> getJavaFxControlBase(@NotNull BiFunction<Node, String, Boolean> validationCallbackBase) {

        var possiblyLinkedObjects = possibleObjectsSupplier.get();
        var actuallyLinkedObjects = getLinkedValues();
        var newLinkedObjects = new ArrayList<>(actuallyLinkedObjects);

        var container = new VBox();
        var checkboxes = new ArrayList<Pair<CheckBox, T>>();
        for (var obj : possiblyLinkedObjects) {
            var checkbox = new CheckBox();
            checkbox.selectedProperty().setValue(actuallyLinkedObjects.contains(obj));
            checkbox.setText(obj.toString());
            container.getChildren().add(checkbox);
            checkboxes.add(new Pair<>(checkbox, obj));
        }
        container.setPadding(new Insets(10));

        var button = new Button();
        Runnable updateButtonText = () -> button.setText("Select (" + newLinkedObjects.size() + "/" + possiblyLinkedObjects.size() + ")");
        updateButtonText.run();

        Supplier<Boolean> validationCallback = () -> {
            // NOTE: this is kinda inefficient (or is it 🤔)
            newLinkedObjects.clear();
            for (var checkbox : checkboxes) {
                if (checkbox.getKey().selectedProperty().get()) {
                    newLinkedObjects.add(checkbox.getValue());
                }
            }
            return validationCallbackBase.apply(button, setLinkedValues(newLinkedObjects));
        };

        button.setOnAction(event -> {
            Alert objectDialog = new Alert(Alert.AlertType.CONFIRMATION);

            objectDialog.setTitle("Select");
            objectDialog.setHeaderText(required ? "Choose at least one" : "");
            objectDialog.getDialogPane().setContent(container);
            objectDialog.setGraphic(null);

            objectDialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType != ButtonType.OK) {
                    return;
                }
                // NOTE: this is kinda inefficient
                if (validationCallback.get()) {
                    onValuesUpdated.accept(actuallyLinkedObjects, newLinkedObjects);
                    updateButtonText.run();
                }
            });
        });
        return new Pair<>(button, validationCallback);
    }
}
