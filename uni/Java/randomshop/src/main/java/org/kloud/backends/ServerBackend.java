package org.kloud.backends;

import org.kloud.daos.server.BasicServerDAO;
import org.kloud.flowcontrollers.LocalLoginController;

public class ServerBackend extends AbstractBackend {

    public ServerBackend() {
        userStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/users";
            }
        };
        productStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/products";
            }
        };
        orderedProductStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/ordered_products";
            }
        };
        warehouseStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/warehouses";
            }
        };
        ordersStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/orders";
            }
        };
        commentStorage = new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/comments";
            }
        };

        loginController = new LocalLoginController(userStorage);
    }
}
