package org.kloud.backends;

import org.kloud.daos.*;
import org.kloud.flowcontrollers.LocalLoginController;

public class DBBackend extends AbstractBackend {

    public DBBackend() {
        userStorage = new DBUsersDAO();
        productStorage = new DBProductsDAO();
        warehouseStorage = new DBWarehousesDAO();
        loginController = new LocalLoginController(userStorage);
    }
}
