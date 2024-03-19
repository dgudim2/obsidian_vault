package org.kloud.utils;

import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;

public abstract class AbstractStorage {

    protected BasicDAO<User> userStorage;
    protected BasicDAO<Product> productStorage;

    protected BasicDAO<Warehouse> warehouseStorage;

    public BasicDAO<User> getUserStorage() {
        return userStorage;
    }

    public BasicDAO<Product> getProductStorage() {
        return productStorage;
    }

    public BasicDAO<Warehouse> getWarehouseStorage() {
        return warehouseStorage;
    }
}
