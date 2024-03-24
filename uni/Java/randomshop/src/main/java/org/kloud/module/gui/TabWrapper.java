package org.kloud.module.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.Breakpoint;
import org.kloud.utils.BasicDAO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TabWrapper<T extends BaseModel> {

    public final ObjectProperty<T> selectedObject = new SimpleObjectProperty<>(null);
    protected final BasicDAO<T> objectsDao;
    protected final ListView<T> objectList;
    protected final Button saveButton;

    public TabWrapper(@NotNull String objectName,
                      @NotNull List<Class<? extends T>> possibleObjects,
                      @NotNull BasicDAO<T> objectsDao,
                      @NotNull Pane objectEditArea,
                      @NotNull ListView<T> objectList,
                      @NotNull Button deleteButton,
                      @NotNull Button addButton,
                      @NotNull Button saveButton) {
        this.objectsDao = objectsDao;
        this.objectList = objectList;
        this.saveButton = saveButton;

        objectList.getItems().addAll(objectsDao.getObjects());

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(objectEditArea.widthProperty());
        pane.prefHeightProperty().bind(objectEditArea.heightProperty());
        objectEditArea.getChildren().add(pane);
        saveButton.setVisible(false);

        objectList.getSelectionModel().selectedItemProperty().addListener((observableValue, object, newObject) -> selectedObject.set(newObject));

        deleteButton.setDisable(true);
        selectedObject.addListener((observableValue, oldObject, newObject) -> {
            deleteButton.setDisable(newObject == null);
            saveButton.setVisible(newObject != null);
            pane.removeFirstRow();
            if (newObject != null) {
                pane.addRow(loadItemsForObject(newObject));
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            Alert objectDialog = new Alert(Alert.AlertType.CONFIRMATION);
            objectDialog.setTitle("Delete a " + objectName);
            objectDialog.setHeaderText("Delete '" + selectedObject.get() + "'?");
            objectDialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType != ButtonType.OK) {
                    return;
                }
                var prod = selectedObject.get();
                objectsDao.removeObject(prod);
                objectList.getItems().remove(prod);
                objectList.getSelectionModel().clearSelection();
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

                ComboBox<String> objectSelector = new ComboBox<>();
                List<String> objectNames = new ArrayList<>(possibleObjects.size());
                for (var object : possibleObjects) {
                    try {
                        objectNames.add(String.valueOf(object.getField("NAME").get(null)));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        objectNames.add(String.valueOf(object));
                    }
                }
                objectSelector.setItems(FXCollections.observableList(objectNames));
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
            var fieldControl = field.getJavaFxControl();
            fxControlHandlers.add(fieldControl.getValue());
            BootstrapColumn column = new BootstrapColumn(fieldControl.getKey());
            column.setBreakpointColumnWidth(Breakpoint.XLARGE, 3);
            column.setBreakpointColumnWidth(Breakpoint.LARGE, 4);
            column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
            column.setBreakpointColumnWidth(Breakpoint.XSMALL, 12);
            row.addColumn(column);
        }

        System.out.println("Loaded " + fields.size() + " fields for '" + object + "'");

        saveButton.setOnAction(actionEvent -> {
            boolean isValid = true;
            for (var fxControlHandler : fxControlHandlers) {
                boolean fieldValid = fxControlHandler.get();
                isValid = isValid && fieldValid;
            }
            if (isValid) {
                objectsDao.addOrUpdateObject(object);
                objectList.refresh();
            }
        });

        return row;
    }

    public boolean hasUnsavedChanges() {
        return objectList.getItems().size() != objectsDao.getObjects().size() || !objectsDao.isLatestVersionSaved();
    }
}
