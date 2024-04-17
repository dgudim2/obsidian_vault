package org.kloud.backends;

import org.kloud.daos.file.*;
import org.kloud.daos.server.UsersServerDAO;
import org.kloud.flowcontrollers.LocalLoginController;

public class ServerBackend extends AbstractBackend {

    public ServerBackend() {
        userStorage = new UsersServerDAO();

        productStorage = new FileProductsDAO();
        orderedProductStorage = new FileOrderedProductsDAO();
        warehouseStorage = new FileWarehousesDAO();
        ordersStorage = new FileOrdersDAO();
        commentStorage = new FileCommentsDAO();

        loginController = new LocalLoginController(userStorage);
    }
}
