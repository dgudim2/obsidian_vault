package org.kloud.backends;

import org.kloud.daos.BasicDAO;
import org.kloud.daos.BasicServerDAO;
import org.kloud.flowcontrollers.LocalLoginController;
import org.kloud.flowcontrollers.LoginController;
import org.kloud.model.Comment;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;

public class ServerBackend extends AbstractBackend {

    @Override
    protected BasicDAO<User> makeUserStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/users";
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeProductStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/products";
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeOrderedProductStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/ordered_products";
            }
        };
    }

    @Override
    protected BasicDAO<Warehouse> makeWarehouseStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/warehouses";
            }
        };
    }

    @Override
    protected BasicDAO<Comment> makeCommentStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/comments";
            }
        };
    }

    @Override
    protected BasicDAO<Order> makeOrderStorage() {
        return new BasicServerDAO<>() {
            @Override
            protected String getEndpoint() {
                return "api/orders";
            }
        };
    }

    @Override
    protected LoginController makeLoginController() {
        return new LocalLoginController(userStorage);
    }
}
