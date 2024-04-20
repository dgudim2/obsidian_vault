package org.kloud.module.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
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
import org.kloud.model.enums.OrderStatus;
import org.kloud.model.product.Product;
import org.kloud.model.user.Customer;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapPane;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.module.gui.components.TabWrapper;
import org.kloud.utils.Conf;
import org.kloud.utils.Logger;
import org.kloud.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
    public Button changeOrderStatusButton;
    @FXML
    public Button cancelOrderButton;


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

        var conf = Conf.getInstance();

        productTabWrapper = new TabWrapper<>(
                "product",
                productsTab,
                Product.PRODUCTS,
                conf.storageBackend.get().getProductStorage(),
                productEditArea,
                productList,
                deleteProductButton,
                addProductButton,
                saveProductButton,
                () -> Conf.getLoginController().hasCapability(UserCapability.WRITE_PRODUCTS),
                product -> Conf.getLoginController().hasCapability(UserCapability.WRITE_PRODUCTS),
                product -> true // Always can read all products
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
                saveUserButton,
                () -> Conf.getLoginController().hasCapability(UserCapability.WRITE_CUSTOMERS)
                        && Conf.getLoginController().hasCapability(UserCapability.WRITE_MANAGERS)
                        && Conf.getLoginController().hasCapability(UserCapability.WRITE_ADMINS),
                user -> {
                    if (user == Conf.getLoginController().loggedInUser.get()) {
                        return true;
                    }
                    if (user instanceof Manager m) {
                        if (!Conf.getLoginController().hasCapability(UserCapability.WRITE_MANAGERS)) {
                            return false;
                        }
                        if (m.isAdmin.get() || m.isSuperAdmin.get()) {
                            return Conf.getLoginController().hasCapability(UserCapability.WRITE_ADMINS);
                        }
                    }
                    if (user instanceof Customer) {
                        return Conf.getLoginController().hasCapability(UserCapability.WRITE_CUSTOMERS);
                    }
                    return true;
                },
                user -> { // Hide some users
                    if (user == Conf.getLoginController().loggedInUser.get()) {
                        return true;
                    }
                    if (user instanceof Manager m) {
                        if (!Conf.getLoginController().hasCapability(UserCapability.READ_MANAGERS)) {
                            return false;
                        }
                        if (m.isAdmin.get() || m.isSuperAdmin.get()) {
                            return Conf.getLoginController().hasCapability(UserCapability.READ_ADMINS);
                        }
                    }
                    if (user instanceof Customer) {
                        return Conf.getLoginController().hasCapability(UserCapability.READ_CUSTOMERS);
                    }
                    return true;
                }
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
                saveWarehouseButton,
                () -> Conf.getLoginController().hasCapability(UserCapability.WRITE_OTHER_WAREHOUSES),

                warehouse -> Conf.getLoginController().canActOnOtherUser(warehouse.assignedManager.getLinkedValue(), UserCapability.WRITE_OTHER_WAREHOUSES) ||
                        Conf.getLoginController().canActOnSelf(warehouse.assignedManager.getLinkedValue(), UserCapability.RW_SELF_WAREHOUSES),

                warehouse -> Conf.getLoginController().canActOnOtherUser(warehouse.assignedManager.getLinkedValue(), UserCapability.READ_OTHER_WAREHOUSES) ||
                        Conf.getLoginController().canActOnSelf(warehouse.assignedManager.getLinkedValue(), UserCapability.RW_SELF_WAREHOUSES)
        );

        enableDisableTabs();

        userTabWrapper.setSelectedObjectListener((newObject) -> changeUserPasswordButton.setDisable(newObject == null));
        changeUserPasswordButton.setDisable(true);
        changeUserPasswordButton.setOnAction(actionEvent -> {

            User loggedInUser = Conf.getLoginController().loggedInUser.get();

            boolean adminLoggedInAndNotCurrentUser =
                    loggedInUser instanceof Manager m && m.isAdmin.get()
                            && !Objects.equals(loggedInUser, userTabWrapper.getSelectedObject());

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
            passwordDialog.setHeaderText("Changing password for " + Objects.requireNonNull(userTabWrapper.getSelectedObject()).login.get());
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

        initOrdersTab();
        initUserLoginTab();
    }

    private void enableDisableTabs() {
        User loggedInUser = Conf.getLoginController().loggedInUser.get();
        if (loggedInUser == null) {
            warehouseTabWrapper.setEnabled(false);
            productTabWrapper.setEnabled(false);
            userTabWrapper.setEnabled(false);
            ordersTab.setDisable(true);
            return;
        }

        var caps = loggedInUser.getUserCaps();

        warehouseTabWrapper.setEnabled(caps.contains(UserCapability.RW_SELF_WAREHOUSES) || caps.contains(UserCapability.READ_OTHER_WAREHOUSES));
        productTabWrapper.setEnabled(true);
        ordersTab.setDisable(!caps.contains(UserCapability.RW_SELF_ORDERS) && !caps.contains(UserCapability.READ_OTHER_ORDERS));
        userTabWrapper.setEnabled(true);
    }

    private void initOrdersTab() {
        addToCartButton.setOnAction(event -> {
            var existingCart = Conf.getStorage().getOrderStorage().getObjects()
                    .stream().filter(order -> order.status.get() == OrderStatus.CART)
                    .findFirst()
                    .orElseGet(() -> {
                        var order = new Order();
                        order.orderedByUser.set(Conf.getLoginController().loggedInUser.get().id);
                        return order;
                    });
            // NOTE: This isn't atomic, horrible
            var selectedProduct = productTabWrapper.getSelectedObject();
            Objects.requireNonNull(selectedProduct);
            if (productTabWrapper.removeObject(selectedProduct)) {
                // Removed from regular list
                if (Conf.getStorage().getOrderedProductStorage().addOrUpdateObject(selectedProduct)) {
                    // Added to ordered list
                    existingCart.orderedProducts.addLinkedValue(selectedProduct);
                    if (!Conf.getStorage().getOrderStorage().addOrUpdateObject(existingCart)) {
                        Logger.warn("Order could not be created/updated because of saving inconsistencies");
                    }
                } else {
                    Logger.warn(selectedProduct + " is GONE because of saving inconsistencies");
                }
            }
        });

        BootstrapPane pane = new BootstrapPane();
        pane.prefWidthProperty().bind(orderEditArea.widthProperty());
        pane.prefHeightProperty().bind(orderEditArea.heightProperty());
        orderEditArea.getChildren().add(pane);

        ordersTab.selectedProperty().addListener((observable, __, selected) -> {
            if (selected) {
                ordersList.getItems().setAll(Conf.getStorage()
                        .getOrderStorage()
                        .getWithFilter(order ->
                                Conf.getLoginController().canActOnSelf(order.orderedByUser.getLinkedValue(), UserCapability.RW_SELF_ORDERS) ||
                                        Conf.getLoginController().canActOnOtherUser(order.orderedByUser.getLinkedValue(), UserCapability.READ_OTHER_ORDERS) ||
                                        Conf.getLoginController().canActOnSelf(order.assignedManager.getLinkedValue(), UserCapability.RW_SELF_ASSIGNED_ORDERS) ||
                                        Conf.getLoginController().canActOnOtherUser(order.assignedManager.getLinkedValue(), UserCapability.READ_OTHER_ASSIGNED_ORDERS)));
            }
        });

        Consumer<@Nullable Order> updateButtons = order -> {

            deleteOrderButton.setDisable(order == null
                    || (order.status.get() != OrderStatus.CART && order.status.get() != OrderStatus.PLACED)
                    || (!Conf.getLoginController().canActOnOtherUser(order.assignedManager.getLinkedValue(), UserCapability.WRITE_OTHER_ASSIGNED_ORDERS)
                    && !Conf.getLoginController().canActOnSelf(order.assignedManager.getLinkedValue(), UserCapability.RW_SELF_ASSIGNED_ORDERS)
                    && !Conf.getLoginController().canActOnOtherUser(order.orderedByUser.getLinkedValue(), UserCapability.WRITE_OTHER_ORDERS)
                    && !Conf.getLoginController().canActOnSelf(order.orderedByUser.getLinkedValue(), UserCapability.RW_SELF_ORDERS)));

            cancelOrderButton.setDisable(order == null || order.status.get() != OrderStatus.PLACED); // Only placed orders can be canceled

            if (order == null) {
                changeOrderStatusButton.setDisable(true);
                return;
            }

            var hasPermissionToChangeStatus =
                    order.status.get() == OrderStatus.CART && Conf.getLoginController().isLoggedInUser(order.orderedByUser.getLinkedValue()) ||
                            Conf.getLoginController().canActOnSelf(order.assignedManager.getLinkedValue(), UserCapability.RW_SELF_ASSIGNED_ORDERS) ||
                            Conf.getLoginController().canActOnOtherUser(order.assignedManager.getLinkedValue(), UserCapability.WRITE_OTHER_ASSIGNED_ORDERS);

            changeOrderStatusButton.setDisable(!hasPermissionToChangeStatus ||
                    (order.assignedManager.getLinkedValue() == null && order.status.get() != OrderStatus.CART));

            switch (order.status.get()) {
                case CART -> changeOrderStatusButton.setText("Place order");
                case PLACED -> changeOrderStatusButton.setText("Mark 'in progress'");
                case IN_PROGRESS -> changeOrderStatusButton.setText("Ship order");
                case SHIPPED -> changeOrderStatusButton.setText("Confirm delivery");
                case DELIVERED -> {
                    changeOrderStatusButton.setText("Delivered!");
                    changeOrderStatusButton.setDisable(true);
                }
                case CANCELLED -> {
                    changeOrderStatusButton.setText("Cancelled");
                    changeOrderStatusButton.setDisable(true);
                }
            }
        };

        Consumer<@Nullable Order> reloadUI = order -> {
            pane.clear();
            updateButtons.accept(order);

            if (order == null) {
                return;
            }

            var orderedProducts = order.orderedProducts.getLinkedValues();

            pane.addRow(new BootstrapRow(new BootstrapColumn("Ordered by: " + order.orderedByUser.getLinkedValue())));
            pane.addRow(new BootstrapRow(new BootstrapColumn("Status: " + order.status)));

            if (order.assignedManager.isVisibleInUI()) {
                pane.addRow(new BootstrapRow(new BootstrapColumn("Assigned manager: " + order.assignedManager.getLinkedValue())));
            }

            pane.addRow(new BootstrapRow(new BootstrapColumn("")));
            pane.addRow(new BootstrapRow(new BootstrapColumn("Products (" + orderedProducts.size() + "):")));

            BootstrapRow row = new BootstrapRow();

            for (var product : orderedProducts) {
                row.addColumn(new BootstrapColumn(product.toString()));
            }

            pane.addRow(row);
        };

        changeOrderStatusButton.setOnAction(event -> {
            var order = ordersList.getSelectionModel().getSelectedItem();

            if (order == null) {
                return;
            }

            if (order.status.get() != OrderStatus.DELIVERED) {
                order.status.set(OrderStatus.values()[order.status.get().ordinal() + 1]);
            }

            Conf.getStorage().getOrderStorage().addOrUpdateObject(order);

            reloadUI.accept(order);
        });

        cancelOrderButton.setOnAction(event -> {
            var selectedOrder = ordersList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null && selectedOrder.status.get() == OrderStatus.PLACED) {
                selectedOrder.status.set(OrderStatus.CANCELLED);
                reloadUI.accept(selectedOrder);

                // TODO: Check status?
                Conf.getStorage().getOrderStorage().addOrUpdateObject(selectedOrder);
                for (var orderedProduct : selectedOrder.orderedProducts.getLinkedValues()) {
                    // Add products back to list but also leave them in ordered products table for information purposes
                    productTabWrapper.addOrUpdateObject(orderedProduct);
                }
            }
        });

        deleteOrderButton.setOnAction(event -> {
            var selectedOrder = ordersList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null &&
                    (selectedOrder.status.get() == OrderStatus.CART ||
                            selectedOrder.status.get() == OrderStatus.PLACED)) {

                if(Conf.getStorage().getOrderStorage().removeObject(selectedOrder)) {
                    ordersList.getItems().remove(selectedOrder);
                    ordersList.getSelectionModel().clearSelection();

                    for (var orderedProduct : selectedOrder.orderedProducts.getLinkedValues()) {
                        if (productTabWrapper.addOrUpdateObject(orderedProduct)) {
                            Conf.getStorage().getOrderedProductStorage().removeObject(orderedProduct);
                        }
                    }
                }
            }
        });

        updateButtons.accept(null);

        ordersList.getSelectionModel().selectedItemProperty().addListener((observable, prevOrder, order) -> {
            reloadUI.accept(order);
        });
    }

    @NotNull
    private TreeItem<String> addCommentNode(@NotNull TreeItem<String> parentNode,
                                            @Nullable Comment parentComment,
                                            @NotNull Comment thisComment,
                                            @NotNull Product product) {
        var thisNode = new TreeItem<>("", new Label(thisComment.toString()));
        var replyAction = new MenuItem("Reply");
        var deleteAction = new MenuItem("Delete");
        var editAction = new MenuItem("Edit");
        ContextMenu contextMenu;
        if (Conf.getLoginController()
                .canActOnOtherUser(thisComment.author.getLinkedValue(), UserCapability.WRITE_OTHER_COMMENTS)) {
            contextMenu = new ContextMenu(replyAction, editAction, deleteAction);
        } else {
            contextMenu = new ContextMenu(replyAction);
        }
        replyAction.setOnAction(event -> leaveOrEditComment(thisNode, thisComment, null, null, product));
        deleteAction.setOnAction(event -> deleteComment(parentNode, parentComment, thisNode, thisComment, product));
        editAction.setOnAction(event -> leaveOrEditComment(thisNode, thisComment, thisNode, thisComment, product));

        // TODO: This is semi-ideal, right click will only work on the label
        // maybe set preferred width for the label??
        thisNode.getGraphic().setOnContextMenuRequested(e -> contextMenu.show(thisNode.getGraphic(), e.getScreenX(), e.getScreenY()));
        thisNode.getGraphic().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Alert viewCommentDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                viewCommentDialog.setTitle("Comment on " + product);
                viewCommentDialog.setHeaderText("View comment");
                BootstrapPane pane = new BootstrapPane();
                pane.setPrefWidth(350);
                pane.setPrefHeight(150);
                pane.addRow(thisComment.loadReadonlyGui());

                viewCommentDialog.getDialogPane().setContent(pane);
                viewCommentDialog.showAndWait();
            }
        });
        parentNode.getChildren().add(thisNode);
        return thisNode;
    }

    private void loadCommentLayer(@NotNull TreeItem<String> parentNode,
                                  @Nullable Comment parentComment,
                                  @NotNull List<Comment> childComments,
                                  @NotNull Product product) {
        for (var childComment : childComments) {

            if (!Conf.getLoginController().canActOnOtherUser(childComment.author.getLinkedValue(), UserCapability.READ_OTHER_COMMENTS) ||
                    !Conf.getLoginController().canActOnSelf(childComment.author.getLinkedValue(), UserCapability.RW_SELF_COMMENTS)) {
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

    private void leaveOrEditComment(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment,
                                    @Nullable TreeItem<String> thisNode, @Nullable Comment thisComment,
                                    @NotNull Product product) {
        Alert addCommentDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        addCommentDialog.setTitle("Comment on " + product);
        var addButton = (Button) addCommentDialog.getDialogPane().lookupButton(ButtonType.YES);

        boolean isEditing;
        if (thisComment == null) {
            isEditing = false;
            addButton.setText("Add");
            // We are leaving a new comment
            addCommentDialog.setHeaderText(parentComment == null ? "Add comment" : "Reply to \"" + parentComment + "\"");
            thisComment = new Comment();
            thisComment.author.set(Conf.getLoginController().loggedInUser.get().id);
        } else {
            isEditing = true;
            addButton.setText("Save");
            // We are editing a comment
            addCommentDialog.setHeaderText(parentComment == null ? "Edit comment" : "Edit reply to \"" + parentComment + "\"");
        }

        BootstrapPane pane = new BootstrapPane();
        pane.setPrefWidth(350);
        pane.setPrefHeight(150);

        @NotNull Comment finalThisComment = thisComment;
        pane.addRow(thisComment.loadEditableGui(addButton, Conf.getStorage().getCommentStorage(), () -> {
            if (parentComment == null) {
                if (!isEditing) {
                    Objects.requireNonNull(productTabWrapper.getSelectedObject()).comments.addLinkedValue(finalThisComment);
                }
                // NOTE: This is wrong, we can save an invalid object by leaving a comment on it (validate in addOrUpdateObject or here maybe)
                Conf.getStorage().getProductStorage().addOrUpdateObject(Objects.requireNonNull(productTabWrapper.getSelectedObject()));
            } else {
                if (!isEditing) {
                    parentComment.children.addLinkedValue(finalThisComment);
                }
                // NOTE: We are saving the child first, then updating the parent,
                // can't do it in one go because getByIds won't return our newly created child, need to save it first
                // Maybe add a flag 'save' to addOrUpdateObject, but I don't know how to handle it gracefully in DBDAO because we do SQL UPDATE
                // when updating the object. Maybe a separate queue??
                // TLDR: Skip this for now
                Conf.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            }
            if (!isEditing) {
                addCommentNode(parentNode, parentComment, finalThisComment, product);
            } else {
                Objects.requireNonNull(thisNode).setValue(finalThisComment.toString());
            }
            addCommentDialog.close();
        }));

        addCommentDialog.getDialogPane().setContent(pane);
        addCommentDialog.showAndWait();
    }

    private void deleteComment(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment,
                               @NotNull TreeItem<String> thisNode, @NotNull Comment thisComment, @NotNull Product product) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        confirmDialog.setTitle("Delete a comment");
        confirmDialog.setHeaderText("Delete " + thisComment + "?");
        var result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            parentNode.getChildren().remove(thisNode);
            if (parentComment != null) {
                parentComment.children.removeLinkedValue(thisComment);
                Conf.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            } else {
                product.comments.removeLinkedValue(thisComment);
                Conf.getStorage().getProductStorage().addOrUpdateObject(product);
            }
            deleteCommentChildrenRecursive(thisComment);
            Conf.getStorage().getCommentStorage().removeById(thisComment.id);
        }
    }

    private void deleteCommentChildrenRecursive(@NotNull Comment thisComment) {
        for (var child : thisComment.children.getLinkedValues()) {
            deleteCommentChildrenRecursive(child);
        }
        // NOTE: This is inefficient, collect all ids and delete in one batch maybe
        Conf.getStorage().getCommentStorage().removeByIds(thisComment.children.get().backingList);
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

            var loginController = Conf.getLoginController();
            User loggedInUserV = loginController.loggedInUser.get();

            if (loggedInUserV == null) {
                var login = userField.getText();
                var password = passwordField.getText();

                var loginResult = loginController.tryLogin(login, password);

                invalidUserLabel.setVisible(!loginResult);

            } else {
                if (canFinish()) {
                    loginController.logout();
                    invalidUserLabel.setVisible(false);
                }
            }
        });

        Conf.getLoginController().loggedInUser.addListener((observable, oldValue, newUser) -> {
            if (newUser == null) {
                warehouseTabWrapper.reset();
                productTabWrapper.reset();
                userTabWrapper.reset();
                Conf.close();
            }

            enableDisableTabs();

            if (Conf.getLoginController().hasCapability(UserCapability.RW_SELF_COMMENTS) ||
                    Conf.getLoginController().hasCapability(UserCapability.READ_OTHER_COMMENTS)) {
                // Only enable the comments section if our current user has access to it
                productTabWrapper.setSelectedObjectListener(this::loadCommentsForProduct);
                addCommentButton.setOnAction(event -> leaveOrEditComment(
                        commentsTree.getRoot(), null,
                        null, null,
                        Objects.requireNonNull(productTabWrapper.getSelectedObject())));
            } else {
                productTabWrapper.setSelectedObjectListener(null);
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

    private boolean canFinish() {
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
            Conf.close();
        }
        return res;
    }

    @Override
    public boolean notifyCloseRequest() {
        return canFinish();
    }
}
