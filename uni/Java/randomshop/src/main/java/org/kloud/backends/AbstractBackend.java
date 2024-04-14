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

    protected BasicDAO<User> userStorage;
    protected BasicDAO<Product> productStorage;
    protected BasicDAO<Product> orderedProductStorage;
    protected BasicDAO<Warehouse> warehouseStorage;
    protected BasicDAO<Comment> commentStorage;
    protected LoginController loginController;
    protected BasicDAO<Order> ordersStorage;

    public boolean isValid() {
        return userStorage.isValid() &&
                productStorage.isValid() &&
                orderedProductStorage.isValid() &&
                warehouseStorage.isValid() &&
                ordersStorage.isValid() &&
                commentStorage.isValid();
    }

    public void close() {
        userStorage.close();
        productStorage.close();
        orderedProductStorage.close();
        warehouseStorage.close();
        ordersStorage.close();
        commentStorage.close();
        Logger.info("Closed(reset) backend");
    }
}
