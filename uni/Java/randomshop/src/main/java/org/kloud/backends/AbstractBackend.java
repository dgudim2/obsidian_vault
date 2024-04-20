package org.kloud.backends;

import lombok.Getter;
import org.kloud.daos.BasicDAO;
import org.kloud.flowcontrollers.LoginController;
import org.kloud.model.Comment;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;
import org.kloud.utils.Logger;

/**
 * Base class that holds all DAOs for accessing objects
 */
@Getter
public abstract class AbstractBackend {

    protected final BasicDAO<User> userStorage;
    protected final BasicDAO<Product> productStorage;
    protected final BasicDAO<Product> orderedProductStorage;
    protected final BasicDAO<Warehouse> warehouseStorage;
    protected final BasicDAO<Comment> commentStorage;
    protected final LoginController loginController;
    protected final BasicDAO<Order> orderStorage;

    protected abstract BasicDAO<User> makeUserStorage();
    protected abstract BasicDAO<Product> makeProductStorage();
    protected abstract BasicDAO<Product> makeOrderedProductStorage();
    protected abstract BasicDAO<Warehouse> makeWarehouseStorage();
    protected abstract BasicDAO<Comment> makeCommentStorage();
    protected abstract BasicDAO<Order> makeOrderStorage();
    protected abstract LoginController makeLoginController();

    public AbstractBackend() {
        userStorage = makeUserStorage();
        productStorage = makeProductStorage();
        orderedProductStorage = makeOrderedProductStorage();
        warehouseStorage = makeWarehouseStorage();
        commentStorage = makeCommentStorage();
        orderStorage = makeOrderStorage();
        loginController = makeLoginController();
    }

    public boolean isValid() {
        return userStorage.isValid() &&
                productStorage.isValid() &&
                orderedProductStorage.isValid() &&
                warehouseStorage.isValid() &&
                orderStorage.isValid() &&
                commentStorage.isValid();
    }

    public void close() {
        userStorage.close();
        productStorage.close();
        orderedProductStorage.close();
        warehouseStorage.close();
        orderStorage.close();
        commentStorage.close();
        Logger.info("Closed(reset) backend");
    }
}
