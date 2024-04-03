package org.kloud.module.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.enums.BookGenre;
import org.kloud.model.product.Book;
import org.kloud.module.gui.Entrypoint;
import org.kloud.module.gui.TabWrapper;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Logger;
import org.kloud.utils.Utils;

import java.io.IOException;
import java.util.List;

import static org.kloud.utils.Utils.setDanger;

public class EntrypointController implements BaseController {

    @FXML
    public ListView<Book> productList;
    @FXML
    public Pane productEditArea;
    @FXML
    public Button deleteProductButton;
    @FXML
    public Button addProductButton;
    @FXML
    public Button saveProductButton;
    @FXML
    public TabPane tabContainer;
    @FXML
    public Tab productsTab;
    @FXML
    public Button settingsButton;
    @FXML
    public AnchorPane root;
    @FXML
    public Button filterButton;

    private TabWrapper booksTabWrapper;

    @Nullable
    private Boolean availabilityFilter = null;
    @Nullable
    private Integer startYearFilter = null;
    @Nullable
    private Integer endYearFilter = null;
    @Nullable
    private BookGenre genreFilter = null;

    @FXML
    public void initialize() {
        var conf = ConfigurationSingleton.getInstance();

        booksTabWrapper = new TabWrapper(
                "product",
                productsTab,
                List.of(Book.class),
                conf.bookStorage,
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton
        );

        settingsButton.setOnAction(actionEvent -> {
            try {
                Utils.loadStage(new Stage(), "settings-view.fxml", "Bookshop settings", stage1 -> {
                    stage1.setResizable(false);
                    stage1.initOwner(root.getScene().getWindow());
                    stage1.initModality(Modality.WINDOW_MODAL);
                });
            } catch (IOException e) {
                Logger.error("Error opening settings: " + e.getMessage());
            }
        });

        filterButton.setOnAction(actionEvent -> {
            try {
                var stage = new Stage();

                var controller = new BaseController() {

                    @FXML
                    CheckBox startYearEnabled;
                    @FXML
                    TextField startYear;
                    @FXML
                    CheckBox endYearEnabled;
                    @FXML
                    TextField endYear;
                    @FXML
                    CheckBox genreEnabled;
                    @FXML
                    ComboBox<BookGenre> genre;
                    @FXML
                    CheckBox availabilityEnabled;
                    @FXML
                    CheckBox availability;

                    @FXML
                    Label errorText;

                    @FXML
                    public void initialize() {

                        startYearEnabled.selectedProperty().setValue(startYearFilter != null);
                        endYearEnabled.selectedProperty().setValue(endYearFilter != null);
                        availabilityEnabled.selectedProperty().setValue(availabilityFilter != null);
                        genreEnabled.selectedProperty().setValue(genreFilter != null);

                        startYearEnabled.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            startYear.setDisable(!newValue);
                            if(!newValue) {
                                startYearFilter = null;
                            }
                        });
                        endYearEnabled.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            endYear.setDisable(!newValue);
                            if(!newValue) {
                                endYearFilter = null;
                            }
                        });
                        availabilityEnabled.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            availability.setDisable(!newValue);
                            if(!newValue) {
                                availabilityFilter = null;
                            }
                        });
                        genreEnabled.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            genre.setDisable(!newValue);
                            if(!newValue) {
                                genreFilter = null;
                            }
                        });

                        setDanger(errorText, true);

                        startYear.setText(startYearFilter == null ? "" : startYearFilter.toString());
                        endYear.setText(endYearFilter == null ? "" : endYearFilter.toString());
                        availability.selectedProperty().setValue(availabilityFilter != null && availabilityFilter);
                        genre.getSelectionModel().select(genreFilter == null ? BookGenre.ADVENTURE : genreFilter);

                        startYear.textProperty().addListener((observable, oldValue, newValue) -> {
                            try{
                                startYearFilter = Integer.parseInt(newValue);
                                errorText.setText("");
                            } catch (NumberFormatException e) {
                                errorText.setText(e.getMessage());
                            }
                        });

                        endYear.textProperty().addListener((observable, oldValue, newValue) -> {
                            try{
                                endYearFilter = Integer.parseInt(newValue);
                                errorText.setText("");
                            } catch (NumberFormatException e) {
                                errorText.setText(e.getMessage());
                            }
                        });

                        genre.setItems(FXCollections.observableList(List.of(BookGenre.values())));
                        genre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            genreFilter = newValue;
                        });

                        availability.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            try{
                                availabilityFilter = newValue;
                                errorText.setText("");
                            } catch (NumberFormatException e) {
                                errorText.setText(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public boolean notifyCloseRequest() {
                        boolean allGood = errorText.getText().isEmpty();
                        if(allGood) {
                            booksTabWrapper.setFilter(startYearFilter, endYearFilter, availabilityFilter, genreFilter);
                        }
                        return allGood;
                    }
                };

                FXMLLoader fxmlLoader = new FXMLLoader(Entrypoint.class.getResource("filter-view.fxml"));
                fxmlLoader.setController(controller);
                stage.setTitle("Bookshop filter");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setResizable(false);
                stage.initOwner(root.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setOnCloseRequest(we -> {
                    if (!controller.notifyCloseRequest()) {
                        we.consume();
                    }
                });
                stage.show();
            } catch (IOException e) {
                Logger.error("Error opening filter: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean notifyCloseRequest() {
        try {
            ConfigurationSingleton.getInstance().bookStorage.close();
            Logger.success("Closed db connection");
        } catch (Exception e) {
            Logger.warn("Error closing db connection: " + e.getMessage());
        }
        return true;
    }
}
