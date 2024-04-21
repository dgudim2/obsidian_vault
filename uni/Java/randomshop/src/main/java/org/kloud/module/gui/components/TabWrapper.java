package org.kloud.module.gui.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Wrapper over a tab (user/product/warehouse) handling deletion, creation, displaying and editing of the models
 *
 * @param <T> Objects to display
 */
public class TabWrapper<T extends BaseModel> {

    private final String objectName;

    @NotNull
    private final ObjectProperty<T> selectedObject = new SimpleObjectProperty<>(null);
    @Nullable
    private Consumer<T> selectedObjectChangedUserListener;
    @NotNull
    final Consumer<T> onSelectedObjectChanged;
    @NotNull
    protected final BasicDAO<T> objectsDao;
    @NotNull
    protected final ListView<T> objectList;
    @NotNull
    protected final Tab tab;
    private final Predicate<T> listFilter;

    protected boolean isInitialized = false;

    public TabWrapper(@NotNull String objectName,
                      @NotNull Tab tab,
                      @NotNull List<Class<? extends T>> possibleObjects,
                      @NotNull BasicDAO<T> objectsDao,
                      @NotNull Pane objectEditArea,
                      @NotNull ListView<T> objectList,
                      @NotNull Button deleteButton,
                      @NotNull Button addButton,
                      @NotNull Button saveButton,
                      @NotNull BooleanSupplier addPermission,
                      @NotNull Predicate<T> writePermission,
                      @NotNull Predicate<T> listFilter) {
        this.objectName = objectName;
        this.objectsDao = objectsDao;
        this.objectList = objectList;
        this.tab = tab;
        this.listFilter = listFilter;

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(objectEditArea.widthProperty());
        pane.prefHeightProperty().bind(objectEditArea.heightProperty());
        objectEditArea.getChildren().add(pane);

        saveButton.setVisible(false);

        onSelectedObjectChanged = (newObject) -> {
            addButton.setDisable(!addPermission.getAsBoolean());
            deleteButton.setDisable(newObject == null || !writePermission.test(newObject));
            saveButton.setVisible(newObject != null && writePermission.test(newObject));
            pane.removeFirstRow();
            if (newObject != null) {
                pane.addRow(writePermission.test(newObject)
                        ? newObject.loadEditableGui(saveButton, objectsDao, objectList::refresh, false)
                        : newObject.loadReadonlyGui());
            }
            if (selectedObjectChangedUserListener != null) {
                selectedObjectChangedUserListener.accept(newObject);
            }
        };

        tab.selectedProperty().addListener((observable, __, selected) -> {
            if (selected) {
                if (objectList.getItems().isEmpty()) {
                    isInitialized = true;
                    Logger.debug(objectName + " tab is now active");
                    objectList.getItems().setAll(objectsDao.getWithFilter(listFilter));
                }
                refreshUI();
            }
        });

        objectList.getSelectionModel().selectedItemProperty().addListener((observableValue, object, newObject) -> selectedObject.set(newObject));
        selectedObject.addListener((__, ___, newObject) -> onSelectedObjectChanged.accept(newObject));

        deleteButton.setDisable(true);
        deleteButton.setOnAction(actionEvent -> {
            Alert objectDialog = new Alert(Alert.AlertType.CONFIRMATION);
            objectDialog.setTitle("Delete a " + objectName);
            objectDialog.setHeaderText("Delete '" + selectedObject.get() + "'?");
            objectDialog.setContentText(selectedObject.get().isSafeToDelete());
            objectDialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType != ButtonType.OK) {
                    return;
                }
                removeObject(selectedObject.get());
            });
        });

        if (possibleObjects.size() == 1) {
            addButton.setOnAction(actionEvent -> {
                try {
                    objectList.getItems().add(possibleObjects.get(0).getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            addButton.setOnAction(actionEvent -> {
                Alert objectDialog = new Alert(Alert.AlertType.CONFIRMATION);
                objectDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

                final int[] selectedObjectIndex = {-1};

                List<String> objectNames = new ArrayList<>(possibleObjects.size());
                for (var object : possibleObjects) {
                    try {
                        objectNames.add(String.valueOf(object.getField("NAME").get(null)));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        objectNames.add(String.valueOf(object));
                    }
                }
                ComboBox<String> objectSelector = new ComboBox<>(FXCollections.observableList(objectNames));

                objectSelector.getSelectionModel().selectedIndexProperty().addListener((observableValue, index, newIndex) -> {
                    selectedObjectIndex[0] = (int) newIndex;
                    objectDialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                });
                objectSelector.setPrefWidth(300);

                var container = new HBox();
                container.getChildren().add(objectSelector);

                objectDialog.setTitle("Add a " + objectName);
                objectDialog.setHeaderText("Select a " + objectName + " to add");
                objectDialog.getDialogPane().setContent(container);
                objectDialog.setGraphic(null);

                objectDialog.showAndWait().ifPresent(buttonType -> {
                    if (buttonType != ButtonType.OK) {
                        return;
                    }
                    try {
                        objectList.getItems().add(possibleObjects.get(selectedObjectIndex[0]).getDeclaredConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }
    }

    @Nullable
    public T getSelectedObject() {
        return selectedObject.get();
    }

    public void setSelectedObjectListener(@Nullable Consumer<T> listener) {
        selectedObjectChangedUserListener = listener;
    }

    public boolean removeObject(T object) {
        var removed = objectsDao.removeObject(object);
        objectList.getSelectionModel().clearSelection();
        objectList.getItems().remove(object);
        return removed;
    }

    public boolean addOrUpdateObject(T object) {
        var updated = objectsDao.addOrUpdateObject(object);
        if (!objectList.getItems().contains(object)) {
            objectList.getItems().add(object);
        }
        return updated;
    }

    public void reset() {
        isInitialized = false;
        objectList.getItems().clear();
        Logger.info("Reset tab wrapper for " + objectName);
    }

    public void setEnabled(boolean enabled) {
        tab.setDisable(!enabled);
    }

    public boolean hasUnsavedChanges() {
        return objectsDao.isInitialized() && isInitialized &&
                (objectList.getItems().size() != objectsDao.getWithFilter(listFilter).size() || objectsDao.hasUnsavedChanges());
    }

    public void refreshUI() {
        // Refresh UI elements
        onSelectedObjectChanged.accept(selectedObject.get());
    }
}
