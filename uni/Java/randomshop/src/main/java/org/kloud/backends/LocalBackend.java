package org.kloud.backends;

import org.kloud.daos.FileProductsDAO;
import org.kloud.daos.FileUsersDAO;
import org.kloud.daos.FileWarehousesDAO;
import org.kloud.flowcontrollers.LocalLoginController;

public class LocalBackend extends AbstractBackend {

    public LocalBackend() {
        userStorage = new FileUsersDAO();
        productStorage = new FileProductsDAO();
        warehouseStorage = new FileWarehousesDAO();
        loginController = new LocalLoginController(userStorage);
    }
}
