package org.kloud.utils;

public class LocalStorage extends AbstractStorage {

    public LocalStorage() {
        userStorage = new FileUsersDAO();
        productStorage = new FileProductsDAO();
        warehouseStorage = new FileWarehousesDAO();
    }
}
