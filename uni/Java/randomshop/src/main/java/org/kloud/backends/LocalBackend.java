package org.kloud.backends;

import org.jetbrains.annotations.NotNull;
import org.kloud.daos.BasicDAO;
import org.kloud.daos.BasicFileDAO;
import org.kloud.flowcontrollers.LocalLoginController;
import org.kloud.flowcontrollers.LoginController;
import org.kloud.model.Comment;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;

import java.util.List;

import static org.kloud.utils.Utils.createDefaultUser;

/**
 * Implementation of {@link AbstractBackend} that stores everything on the local drive
 */
public class LocalBackend extends AbstractBackend {

    @Override
    protected BasicDAO<User> makeUserStorage() {
        return new BasicFileDAO<>() {
            @Override
            @NotNull
            protected String getFileName() {
                return "users.dat";
            }

            @Override
            protected @NotNull List<User> readObjectsInternal() {
                return createDefaultUser(super.readObjectsInternal());
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeProductStorage() {
        return new BasicFileDAO<>() {
            @Override
            protected @NotNull String getFileName() {
                return "products.dat";
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeOrderedProductStorage() {
        return new BasicFileDAO<>() {
            @Override
            protected @NotNull String getFileName() {
                return "ordered_products.dat";
            }
        };
    }

    @Override
    protected BasicDAO<Warehouse> makeWarehouseStorage() {
        return new BasicFileDAO<>() {
            @Override
            protected @NotNull String getFileName() {
                return "warehouses.dat";
            }
        };
    }

    @Override
    protected BasicDAO<Comment> makeCommentStorage() {
        return new BasicFileDAO<>() {
            @Override
            protected @NotNull String getFileName() {
                return "comments.dat";
            }
        };
    }

    @Override
    protected BasicDAO<Order> makeOrderStorage() {
        return new BasicFileDAO<>() {
            @Override
            protected @NotNull String getFileName() {
                return "orders.dat";
            }
        };
    }

    @Override
    protected LoginController makeLoginController() {
        return new LocalLoginController(userStorage);
    }
}
