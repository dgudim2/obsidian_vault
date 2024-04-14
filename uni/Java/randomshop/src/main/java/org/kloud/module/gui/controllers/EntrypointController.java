package org.kloud.module.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.common.UserCapability;
import org.kloud.common.datatypes.HashedString;
import org.kloud.model.Comment;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.module.gui.TabWrapper;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Logger;
import org.kloud.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.kloud.utils.Utils.setDanger;

/**
 * Controller for main screen
 */
public class EntrypointController implements BaseController {

    @FXML
    public AnchorPane root;

    // Login page
    @FXML
    public Button settingsButton;
    @FXML
    public Button loginButton;
    @FXML
    public TextField userField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label loginTitle;
    @FXML
    public Label invalidUserLabel;
    @FXML
    public Button changeUserPasswordButton;


    @FXML
    public Button addWarehouseButton;
    @FXML
    public Button addProductButton;
    @FXML
    public Button addUserButton;


    @FXML
    public Button deleteWarehouseButton;
    @FXML
    public Button deleteProductButton;
    @FXML
    public Button deleteUserButton;
    @FXML
    public Button deleteOrderButton;


    @FXML
    public Pane warehouseEditArea;
    @FXML
    public Pane productEditArea;
    @FXML
    public Pane userEditArea;
    @FXML
    public Pane orderEditArea;


    @FXML
    public Tab userLoginTab;
    @FXML
    public Tab warehousesTab;
    @FXML
    public Tab productsTab;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab ordersTab;


    @FXML
    public ListView<Warehouse> warehousesList;
    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<User> userList;
    @FXML
    public ListView<Order> ordersList;


    @FXML
    public Button saveWarehouseButton;
    @FXML
    public Button saveProductButton;
    @FXML
    public Button saveUserButton;
    @FXML
    public Button saveOrderButton;


    @FXML
    public Button addToCartButton;
    @FXML
    public Label lastMessageLabel;

    // Comments in the products tab
    @FXML
    public Label commentsLabel;
    @FXML
    public TreeView<String> commentsTree;
    @FXML
    public Separator commentsSeparator;
    @FXML
    public HBox addToCartCommentBox;
    @FXML
    public Button addCommentButton;

    private TabWrapper<Product> productTabWrapper;
    private TabWrapper<User> userTabWrapper;
    private TabWrapper<Warehouse> warehouseTabWrapper;

    @FXML
    public void initialize() {
        lastMessageLabel.textProperty().bind(Logger.lastMessage);

        var conf = ConfigurationSingleton.getInstance();

        productTabWrapper = new TabWrapper<>(
                "product",
                productsTab,
                Product.PRODUCTS,
                conf.storageBackend.get().getProductStorage(),
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton
        );

        userTabWrapper = new TabWrapper<>(
                "user",
                usersTab,
                User.USERS,
                conf.storageBackend.get().getUserStorage(),
                userEditArea,
                userList,
                deleteUserButton,
                addUserButton,
                saveUserButton
        );

        warehouseTabWrapper = new TabWrapper<>(
                "warehouse",
                warehousesTab,
                List.of(Warehouse.class),
                conf.storageBackend.get().getWarehouseStorage(),
                warehouseEditArea,
                warehousesList,
                deleteWarehouseButton,
                addWarehouseButton,
                saveWarehouseButton
        );

        enableDisableTabs();

        userTabWrapper.selectedObject.addListener((observableValue, oldObject, newObject) -> changeUserPasswordButton.setDisable(newObject == null));
        changeUserPasswordButton.setDisable(true);
        changeUserPasswordButton.setOnAction(actionEvent -> {

            User loggedInUser = ConfigurationSingleton.getLoginController().loggedInUser.get();

            boolean adminLoggedInAndNotCurrentUser =
                    loggedInUser instanceof Manager m && m.isAdmin.get()
                            && !Objects.equals(loggedInUser, userTabWrapper.selectedObject.get());

            Alert passwordDialog = new Alert(Alert.AlertType.CONFIRMATION);

            var oldPassLabel = new Label("Old password: ");
            var oldPassInput = new PasswordField();
            var newPassLabel = new Label("New password: ");
            var newPassInput = new PasswordField();

            var oldPassContainer = new AnchorPane();
            oldPassContainer.getChildren().addAll(oldPassLabel, oldPassInput);
            AnchorPane.setBottomAnchor(oldPassLabel, 1.0);
            AnchorPane.setTopAnchor(oldPassLabel, 1.0);
            AnchorPane.setRightAnchor(oldPassLabel, 200.0);
            AnchorPane.setRightAnchor(oldPassInput, 1.0);
            oldPassContainer.setPadding(new Insets(0, 0, 3, 0));

            var newPassContainer = new AnchorPane();
            newPassContainer.getChildren().addAll(newPassLabel, newPassInput);
            AnchorPane.setBottomAnchor(newPassLabel, 1.0);
            AnchorPane.setTopAnchor(newPassLabel, 1.0);
            AnchorPane.setRightAnchor(newPassLabel, 200.0);
            AnchorPane.setRightAnchor(newPassInput, 1.0);
            newPassContainer.setPadding(new Insets(3, 0, 0, 0));

            var wrongPasswordLabel = new Label();
            wrongPasswordLabel.setAlignment(Pos.CENTER);
            wrongPasswordLabel.setPrefWidth(300);
            wrongPasswordLabel.setVisible(false);
            setDanger(wrongPasswordLabel, true);

            var container = new VBox();
            // Ask for current password for currently logged-in user and for other non-admins
            if (adminLoggedInAndNotCurrentUser) {
                container.getChildren().addAll(newPassContainer, wrongPasswordLabel);
            } else {
                container.getChildren().addAll(oldPassContainer, newPassContainer, wrongPasswordLabel);
            }

            passwordDialog.setTitle("Change password");
            passwordDialog.setHeaderText("Changing password for " + userTabWrapper.selectedObject.get().login.get());
            passwordDialog.getDialogPane().setContent(container);
            passwordDialog.setGraphic(null);

            var positiveButton = passwordDialog.getDialogPane().lookupButton(ButtonType.OK);

            positiveButton.addEventFilter(ActionEvent.ACTION, event -> {
                var oldPass = oldPassInput.getText();
                var newPass = newPassInput.getText();

                if (adminLoggedInAndNotCurrentUser || loggedInUser.checkPassword(oldPass)) {
                    var warn = loggedInUser.pass.set(new HashedString(newPass));
                    if (warn.isEmpty()) {
                        wrongPasswordLabel.setVisible(false);
                        userTabWrapper.refreshUI();
                    } else {
                        wrongPasswordLabel.setText(warn);
                        wrongPasswordLabel.setVisible(true);
                        event.consume();
                    }
                } else {
                    wrongPasswordLabel.setText("Wrong password!");
                    wrongPasswordLabel.setVisible(true);
                    event.consume();
                }
            });

            passwordDialog.showAndWait();
        });

        loadCommentsForProduct(null);
        initUserLoginTab();
    }

    private void enableDisableTabs() {
        User loggedInUser = ConfigurationSingleton.getLoginController().loggedInUser.get();
        if (loggedInUser == null) {
            warehouseTabWrapper.setEnabled(false);
            productTabWrapper.setEnabled(false);
            userTabWrapper.setEnabled(false);
            return;
        }

        var caps = loggedInUser.getUserCaps();

        warehouseTabWrapper.setEnabled(caps.contains(UserCapability.RW_SELF_WAREHOUSES) || caps.contains(UserCapability.READ_OTHER_WAREHOUSES));
        productTabWrapper.setEnabled(caps.contains(UserCapability.RW_SELF_PRODUCTS) || caps.contains(UserCapability.READ_OTHER_PRODUCTS));
        userTabWrapper.setEnabled(true);
    }

    @NotNull
    private TreeItem<String> addCommentNode(@NotNull TreeItem<String> parentNode,
                                            @Nullable Comment parentComment,
                                            @NotNull Comment thisComment,
                                            @NotNull Product product) {
        boolean canEditOther = ConfigurationSingleton.getLoginController().hasUserCapability(UserCapability.WRITE_OTHER_COMMENTS);

        var thisNode = new TreeItem<>("", new Label(thisComment.toString()));
        var replyAction = new MenuItem("Reply");
        var deleteAction = new MenuItem("Delete");
        ContextMenu contextMenu;
        if (ConfigurationSingleton.getLoginController().isLoggedInUser(thisComment.author.getLinkedValue()) && !canEditOther) {
            contextMenu = new ContextMenu(replyAction);
        } else {
            contextMenu = new ContextMenu(replyAction, deleteAction);
        }
        replyAction.setOnAction(event -> leaveComment(thisNode, thisComment, product));
        deleteAction.setOnAction(event -> deleteCommentNode(parentNode, parentComment, thisNode, thisComment, product));

        // TODO: This is semi-ideal, right click will only work on the label
        // maybe set preferred width for the label??
        thisNode.getGraphic().setOnContextMenuRequested(e -> contextMenu.show(thisNode.getGraphic(), e.getScreenX(), e.getScreenY()));
        parentNode.getChildren().add(thisNode);
        return thisNode;
    }

    private void loadCommentLayer(@NotNull TreeItem<String> parentNode,
                                  @Nullable Comment parentComment,
                                  @NotNull List<Comment> childComments,
                                  @NotNull Product product) {
        boolean canReadOther = ConfigurationSingleton.getLoginController().hasUserCapability(UserCapability.READ_OTHER_COMMENTS);
        for (var childComment : childComments) {
            if (ConfigurationSingleton.getLoginController().isLoggedInUser(childComment.author.getLinkedValue()) && !canReadOther) {
                // Skip comments our user doesn't have read access to
                continue;
            }
            var childNode = addCommentNode(parentNode, parentComment, childComment, product);
            // TODO: This is not efficient, we should load maybe up to 2 layers deep
            // but for now I am too lazy to ensure that there are no duplicates on layers
            // just don't leave 30000 comments
            loadCommentLayer(childNode, childComment, childComment.children.getLinkedValues(), product);
        }
    }

    private void loadCommentsForProduct(@Nullable Product product) {
        addToCartCommentBox.setVisible(product != null);
        commentsLabel.setVisible(product != null);
        commentsTree.setVisible(product != null);
        commentsSeparator.setVisible(product != null);

        if (product != null) {
            TreeItem<String> rootItem = new TreeItem<>();
            rootItem.setExpanded(true);
            commentsTree.setRoot(rootItem);
            commentsTree.setShowRoot(false);
            loadCommentLayer(rootItem, null, product.comments.getLinkedValues(), product);
        }
    }

    private void leaveComment(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment, @NotNull Product product) {
        Alert addCommentDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        addCommentDialog.setTitle("Comment on " + product);
        addCommentDialog.setHeaderText(parentComment == null ? "Add comment" : "Reply to \"" + parentComment + "\"");

        var newComment = new Comment();
        newComment.author.set(ConfigurationSingleton.getLoginController().loggedInUser.get().id);

        var addButton = (Button) addCommentDialog.getDialogPane().lookupButton(ButtonType.YES);
        addButton.setText("Add");

        BootstrapPane pane = new BootstrapPane();
        pane.setPrefWidth(350);
        pane.setPrefHeight(150);
        pane.addRow(newComment.loadFulGui(false, addButton, ConfigurationSingleton.getStorage().getCommentStorage(), () -> {
            if (parentComment == null) {
                productTabWrapper.selectedObject.get().comments.addLinkedValue(newComment);
                ConfigurationSingleton.getStorage().getProductStorage().addOrUpdateObject(productTabWrapper.selectedObject.get());
            } else {
                parentComment.children.addLinkedValue(newComment);
                // NOTE: We are saving the child first, then updating the parent,
                // can't do it in one go because getByIds won't return our newly created child, need to save it first
                // Maybe add a flag 'save' to addOrUpdateObject, but I don't know how to handle it gracefully in DBDAO because we do SQL UPDATE
                // when updating the object. Maybe a separate queue??
                // TLDR: Skip this for now
                ConfigurationSingleton.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            }
            addCommentNode(parentNode, parentComment, newComment, product);
            addCommentDialog.close();
        }));

        addCommentDialog.getDialogPane().setContent(pane);
        addCommentDialog.showAndWait();
    }

    private void deleteCommentNode(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment,
                                   @NotNull TreeItem<String> thisNode, @NotNull Comment thisComment, @NotNull Product product) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        confirmDialog.setTitle("Delete a comment");
        confirmDialog.setHeaderText("Delete " + thisComment + "?");
        var result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            parentNode.getChildren().remove(thisNode);
            if (parentComment != null) {
                parentComment.children.removeLinkedValue(thisComment);
                ConfigurationSingleton.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            } else {
                product.comments.removeLinkedValue(thisComment);
                ConfigurationSingleton.getStorage().getProductStorage().addOrUpdateObject(product);
            }
            deleteCommentChildenRecursive(thisComment);
            ConfigurationSingleton.getStorage().getCommentStorage().removeById(thisComment.id);
        }
    }

    private void deleteCommentChildenRecursive(@NotNull Comment thisComment) {
        for (var child : thisComment.children.getLinkedValues()) {
            deleteCommentChildenRecursive(child);
        }
        ConfigurationSingleton.getStorage().getCommentStorage().removeByIds(thisComment.children.get().backingList);
    }

    private void initUserLoginTab() {

        settingsButton.setOnAction(actionEvent -> {
            try {
                Utils.loadStage(new Stage(), "settings-view.fxml", "Randomshop settings", stage1 -> {
                    stage1.setResizable(false);
                    stage1.initOwner(root.getScene().getWindow());
                    stage1.initModality(Modality.WINDOW_MODAL);
                });
            } catch (IOException e) {
                Logger.error("Error opening settings: " + e.getMessage());
            }
        });

        loginButton.setOnAction(actionEvent -> {

            var loginController = ConfigurationSingleton.getLoginController();
            User loggedInUserV = loginController.loggedInUser.get();

            if (loggedInUserV == null) {
                var login = userField.getText();
                var password = passwordField.getText();

                var loginResult = loginController.tryLogin(login, password);

                invalidUserLabel.setVisible(!loginResult);

            } else {
                loginController.logout();
                invalidUserLabel.setVisible(false);
            }
        });

        ConfigurationSingleton.getLoginController().loggedInUser.addListener((observable, oldValue, newUser) -> {
            if (newUser == null) {
                warehouseTabWrapper.reset();
                productTabWrapper.reset();
                userTabWrapper.reset();
                ConfigurationSingleton.close();
            }

            enableDisableTabs();

            if (ConfigurationSingleton.getLoginController().hasUserCapability(UserCapability.RW_SELF_COMMENTS)) {
                // Only enable the comments section if our current user has access to it
                // TODO: Remove listeners on logout
                productTabWrapper.selectedObject.addListener((__, ___, newValue) -> loadCommentsForProduct(newValue));
                addCommentButton.setOnAction(event -> leaveComment(commentsTree.getRoot(), null, productTabWrapper.selectedObject.get()));
            }

            boolean isLoggedIn = newUser != null;

            userField.setDisable(isLoggedIn);
            passwordField.setDisable(isLoggedIn);

            loginButton.setText(isLoggedIn ? "Logout" : "Login");
            loginTitle.setText(isLoggedIn ? "Logged in as " + newUser.login : "Please login");

            settingsButton.setDisable(isLoggedIn);

            setDanger(loginButton, isLoggedIn);
        });
    }

    @Override
    public boolean notifyCloseRequest() {
        String message = "";
        if (productTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved products, exit?";
        } else if (userTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved users, exit?";
        } else if (warehouseTabWrapper.hasUnsavedChanges()) {
            message = "You have some unsaved warehouses, exit?";
        }
        boolean res = true;
        if (!message.isEmpty()) {
            Alert closeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            closeDialog.setTitle("Exit");
            closeDialog.setHeaderText(message);
            var result = closeDialog.showAndWait();
            res = result.isPresent() && result.get() == ButtonType.OK;
        }
        if (res) {
            ConfigurationSingleton.close();
        }
        return res;
    }
}
