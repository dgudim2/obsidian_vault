package org.kloud.module.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.daos.DBBooksDAO;
import org.kloud.model.enums.BookGenre;
import org.kloud.model.product.Book;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.Breakpoint;
import org.kloud.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class TabWrapper {

    @NotNull
    public final ObjectProperty<Book> selectedObject = new SimpleObjectProperty<>(null);
    @NotNull
    protected final DBBooksDAO booksDao;
    @NotNull
    protected final ListView<Book> objectList;
    @NotNull
    protected final Button saveButton;
    @NotNull
    protected final Tab tab;

    protected boolean isInitialized = false;


    @Nullable Integer startYear;
    @Nullable Integer endYear;
    @Nullable Boolean availability;
    @Nullable BookGenre genre;

    public TabWrapper(@NotNull String objectName,
                      @NotNull Tab tab,
                      @NotNull List<Class<? extends Book>> possibleObjects,
                      @NotNull DBBooksDAO booksDao,
                      @NotNull Pane objectEditArea,
                      @NotNull ListView<Book> objectList,
                      @NotNull Button deleteButton,
                      @NotNull Button addButton,
                      @NotNull Button saveButton) {
        this.booksDao = booksDao;
        this.objectList = objectList;
        this.saveButton = saveButton;
        this.tab = tab;

        setEnabled(true);
        tab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && objectList.getItems().isEmpty()) {
                isInitialized = true;
                Logger.debug(objectName + " tab is now active");
                reload();
            }
        });

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
                booksDao.removeObject(prod);
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


    public void setFilter(@Nullable Integer startYear,
                          @Nullable Integer endYear,
                          @Nullable Boolean availability,
                          @Nullable BookGenre genre) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.availability = availability;
        this.genre = genre;
        reload();
    }

    public void reload() {
        objectList.getItems().setAll(booksDao.getObjects().stream()
                .filter(book -> endYear == null || book.publishYear.get() <= endYear)
                .filter(book -> startYear == null || book.publishYear.get() >= startYear)
                .filter(book -> availability == null || book.isAvailable.get() == availability)
                .filter(book -> genre == null || book.genre.get() == genre)
                .toList());
        objectList.refresh();
    }

    public void setEnabled(boolean enabled) {
        tab.setDisable(!enabled);
    }

    private BootstrapRow loadItemsForObject(@NotNull Book object) {
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

        Logger.info("Loaded " + fields.size() + " fields for '" + object + "'");

        saveButton.setOnAction(actionEvent -> {
            boolean isValid = true;
            for (var fxControlHandler : fxControlHandlers) {
                boolean fieldValid = fxControlHandler.get();
                isValid = isValid && fieldValid;
            }
            if (isValid) {
                booksDao.addOrUpdateObject(object);
                reload();
                new Thread(() -> {
                    int i = 0;
                    while(i < 10) {
                        i++;
                        try {
                            Thread.sleep(1000);
                            var items = new ArrayList<>(objectList.getItems());
                            Collections.sort(items, Comparator.comparing(book -> book.title.get(), String::compareTo));
                            System.out.println("Thread: " + items);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        });

        return row;
    }
}
