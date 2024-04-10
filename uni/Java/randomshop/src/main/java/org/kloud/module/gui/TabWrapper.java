package org.kloud.module.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.Breakpoint;
import org.kloud.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Wrapper over a tab (user/product/warehouse) handling deletion, creation, displaying and editing of the models
 * @param <T> Objects to display
 */
public class TabWrapper<T extends BaseModel> {

    private final String objectName;

    @NotNull
    public final ObjectProperty<T> selectedObject = new SimpleObjectProperty<>(null);
    @NotNull
    final Consumer<T> onSelectedObjectChanged;
    @NotNull
    protected final BasicDAO<T> objectsDao;
    @NotNull
    protected final ListView<T> objectList;
    @NotNull
    protected final Button saveButton;
    @NotNull
    protected final Tab tab;

    protected boolean isInitialized = false;

    public TabWrapper(@NotNull String objectName,
                      @NotNull Tab tab,
                      @NotNull List<Class<? extends T>> possibleObjects,
                      @NotNull BasicDAO<T> objectsDao,
                      @NotNull Pane objectEditArea,
                      @NotNull ListView<T> objectList,
                      @NotNull Button deleteButton,
                      @NotNull Button addButton,
                      @NotNull Button saveButton) {
        this.objectName = objectName;
        this.objectsDao = objectsDao;
        this.objectList = objectList;
        this.saveButton = saveButton;
        this.tab = tab;

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(objectEditArea.widthProperty());
        pane.prefHeightProperty().bind(objectEditArea.heightProperty());
        objectEditArea.getChildren().add(pane);
        saveButton.setVisible(false);

        onSelectedObjectChanged = (newObject) -> {
            deleteButton.setDisable(newObject == null);
            saveButton.setVisible(newObject != null);
            saveButton.setDisable(newObject != null && !newObject.hasChanges());
            pane.removeFirstRow();
            if (newObject != null) {
                pane.addRow(loadItemsForObject(newObject));
            }
        };

        tab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && objectList.getItems().isEmpty()) {
                isInitialized = true;
                Logger.debug(objectName + " tab is now active");
                objectList.getItems().setAll(objectsDao.getObjects());
            }
            refreshUI();
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
                var prod = selectedObject.get();
                objectsDao.removeObject(prod);
                objectList.getSelectionModel().clearSelection();
                objectList.getItems().remove(prod);
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

    private BootstrapRow loadItemsForObject(@NotNull T object) {
        BootstrapRow row = new BootstrapRow();
        var fields = object.getFields();
        List<Supplier<Boolean>> fxControlHandlers = new ArrayList<>(fields.size());

        for (var field : fields) {
            var fieldControl = field.getJavaFxControl(false, () -> saveButton.setDisable(!object.hasChanges()));
            fxControlHandlers.add(fieldControl.getValue());
            BootstrapColumn column = new BootstrapColumn(fieldControl.getKey());
            column.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
            column.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
            column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
            column.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
            row.addColumn(column);
        }

        Logger.info("Loaded " + fields.size() + " fields for '" + object + "'");

        saveButton.setOnAction(actionEvent -> {
            boolean isValid = true;
            for (var fxControlHandler : fxControlHandlers) {
                boolean fieldValid = fxControlHandler.get();
                isValid = isValid && fieldValid;
            }
            if (isValid) {
                if (objectsDao.addOrUpdateObject(object)) {
                    objectList.refresh();
                    object.markLatestVersionSaved();
                    saveButton.setDisable(true);
                }
            }
        });

        return row;
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
                (objectList.getItems().size() != objectsDao.getObjects().size() || objectsDao.hasUnsavedChanges());
    }

    public void refreshUI() {
        // Refresh UI elements
        onSelectedObjectChanged.accept(selectedObject.get());
    }
}
