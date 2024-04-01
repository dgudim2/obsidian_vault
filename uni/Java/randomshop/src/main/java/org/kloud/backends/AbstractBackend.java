package org.kloud.backends;

import org.jetbrains.annotations.NotNull;
import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;
import org.kloud.flowcontrollers.LoginController;

public abstract class AbstractBackend {

    protected BasicDAO<User> userStorage;
    protected BasicDAO<Product> productStorage;

    protected BasicDAO<Warehouse> warehouseStorage;

    protected LoginController loginController;

    public BasicDAO<User> getUserStorage() {
        return userStorage;
    }

    public BasicDAO<Product> getProductStorage() {
        return productStorage;
    }

    public BasicDAO<Warehouse> getWarehouseStorage() {
        return warehouseStorage;
    }
    public LoginController getLoginController() {
        return loginController;
    }

    public <V extends BaseModel> BasicDAO<V> getDaoForClass(@NotNull Class<V> klass) {
        if(klass.equals(User.class) || klass.getSuperclass().equals(User.class)) {
            return (BasicDAO<V>) getUserStorage();
        }
        if(klass.equals(Product.class) || klass.getSuperclass().equals(Product.class)) {
            return (BasicDAO<V>) getProductStorage();
        }
        if(klass.equals(Warehouse.class)|| klass.getSuperclass().equals(Warehouse.class)) {
            return (BasicDAO<V>) getWarehouseStorage();
        }
        throw new RuntimeException("No DAO for class: " + klass);
    }

    public boolean isValid() {
        return userStorage.isValid() && productStorage.isValid() && warehouseStorage.isValid();
    }

    public void close() throws Exception {
        userStorage.close();
        productStorage.close();
        warehouseStorage.close();
    }
}
