package org.kloud.backends;

import org.kloud.daos.db.*;
import org.kloud.flowcontrollers.LocalLoginController;

/**
 * Implementation of {@link AbstractBackend} that stores everything in the database
 */
public class DBBackend extends AbstractBackend {

    public DBBackend() {
        userStorage = new DBUsersDAO();
        productStorage = new DBProductsDAO();
        orderedProductStorage = new DBOrderedProductsDao();
        warehouseStorage = new DBWarehousesDAO();
        ordersStorage = new DBOrdersDAO();
        commentStorage = new DBCommentsDAO();
        loginController = new LocalLoginController(userStorage);
    }
}