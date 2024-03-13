package org.kloud.utils;

import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;

public class DaoSingleton {

    private static DaoSingleton instance;

    public final BasicDAO<User> userStorage;
    public final BasicDAO<Product> productStorage;
    public final BasicDAO<Warehouse> warehouseStorage;

    private DaoSingleton() {
        userStorage = new FileUsersDAO();
        productStorage = new FileProductsDAO();
        warehouseStorage = new FileWarehousesDAO();
    }

    public static DaoSingleton getInstance() {
        if(instance == null) {
            instance = new DaoSingleton();
        }
        return instance;
    }

}
