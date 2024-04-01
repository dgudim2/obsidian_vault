package org.kloud.backends;

import org.kloud.daos.DBProductsDAO;
import org.kloud.daos.FileUsersDAO;
import org.kloud.daos.FileWarehousesDAO;
import org.kloud.flowcontrollers.LocalLoginController;

public class StupidBackend extends AbstractBackend {
    public StupidBackend() {
        userStorage = new FileUsersDAO();
        productStorage = new DBProductsDAO();
        warehouseStorage = new FileWarehousesDAO(); // TODO: replace with server :)
        loginController = new LocalLoginController(userStorage);
    }
}
