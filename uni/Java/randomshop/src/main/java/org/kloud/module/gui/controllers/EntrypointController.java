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
import org.jetbrains.annotations.Nullable;
import org.kloud.common.UserCapability;
import org.kloud.common.datatypes.HashedString;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.enums.OrderStatus;
import org.kloud.model.product.Product;
import org.kloud.model.user.Customer;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.module.gui.components.*;
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
    public Pane orderViewArea;


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

    // Comments on the orders tab
    @FXML
    public Label orderCommentsLabel;
    @FXML
    public TreeView<String> orderCommentsTree;
    @FXML
    public Separator orderCommentsSeparator;
    @FXML
    public Button addOrderCommentButton;

    // Wrappers
    private TabWrapper<Product> productTabWrapper;
    private TabWrapper<User> userTabWrapper;
    private TabWrapper<Warehouse> warehouseTabWrapper;
    private CommentsWrapper<Product> productCommentsWrapper;
    private CommentsWrapper<Order> orderCommentsWrapper;

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

        productCommentsWrapper = new CommentsWrapper<>(commentsLabel, commentsTree, commentsSeparator, addCommentButton,
                conf.storageBackend.get().getProductStorage(),
                product -> product.comments,
                () -> productTabWrapper.getSelectedObject());
        addToCartCommentBox.setVisible(false);

        orderCommentsWrapper = new CommentsWrapper<>(orderCommentsLabel, orderCommentsTree, orderCommentsSeparator, addOrderCommentButton,
                conf.storageBackend.get().getOrderStorage(),
                order -> order.comments,
                () -> ordersList.getSelectionModel().getSelectedItem());

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
        pane.prefWidthProperty().bind(orderViewArea.widthProperty());
        pane.prefHeightProperty().bind(orderViewArea.heightProperty());
        orderViewArea.getChildren().add(pane);

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

            if (Conf.getLoginController().hasCapability(UserCapability.RW_SELF_COMMENTS) ||
                    Conf.getLoginController().hasCapability(UserCapability.READ_OTHER_COMMENTS)) {
                // Only enable the comments section if our current user has access to it
                orderCommentsWrapper.loadCommentsForObject(order);
            }
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

                if (Conf.getStorage().getOrderStorage().removeObject(selectedOrder)) {
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

        ordersList.getSelectionModel().selectedItemProperty().addListener((observable, prevOrder, order) -> reloadUI.accept(order));
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
                productTabWrapper.setSelectedObjectListener(product -> {
                    productCommentsWrapper.loadCommentsForObject(product);
                    addToCartCommentBox.setVisible(product != null);
                });
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
