package org.kloud.backends;

import org.kloud.daos.file.*;
import org.kloud.flowcontrollers.LocalLoginController;

/**
 * Implementation of {@link AbstractBackend} that stores everything on the local drive
 */
public class LocalBackend extends AbstractBackend {

    public LocalBackend() {
        userStorage = new FileUsersDAO();
        productStorage = new FileProductsDAO();
        orderedProductStorage = new FileOrderedProductsDAO();
        warehouseStorage = new FileWarehousesDAO();
        ordersStorage = new FileOrdersDAO();
        commentStorage = new FileCommentsDAO();
        loginController = new LocalLoginController(userStorage);
    }
}
