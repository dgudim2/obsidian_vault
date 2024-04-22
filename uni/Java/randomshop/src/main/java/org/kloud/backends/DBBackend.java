package org.kloud.backends;

import org.jetbrains.annotations.NotNull;
import org.kloud.daos.BasicDAO;
import org.kloud.daos.BasicDBDAO;
import org.kloud.flowcontrollers.LocalLoginController;
import org.kloud.flowcontrollers.LoginController;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.product.*;
import org.kloud.model.user.Customer;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.createDefaultUser;

/**
 * Implementation of {@link AbstractBackend} that stores everything in the database
 */
public class DBBackend extends AbstractBackend {

    @Override
    protected BasicDAO<User> makeUserStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "users";
            }

            @Override
            protected @NotNull List<? extends User> getStoredClasses() {
                return List.of(new Manager(BaseModel.DUMMY_ID), new Customer(BaseModel.DUMMY_ID));
            }

            @Override
            protected @NotNull ArrayList<User> readObjectsInternal() {
                return createDefaultUser(super.readObjectsInternal());
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeProductStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "products";
            }

            @Override
            protected @NotNull List<? extends Product> getStoredClasses() {
                return List.of(
                        new Cpu(BaseModel.DUMMY_ID),
                        new Gpu(BaseModel.DUMMY_ID),
                        new Motherboard(BaseModel.DUMMY_ID),
                        new PcCase(BaseModel.DUMMY_ID));
            }
        };
    }

    @Override
    protected BasicDAO<Product> makeOrderedProductStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "ordered_products";
            }

            @Override
            protected @NotNull List<? extends Product> getStoredClasses() {
                return List.of(
                        new Cpu(BaseModel.DUMMY_ID),
                        new Gpu(BaseModel.DUMMY_ID),
                        new Motherboard(BaseModel.DUMMY_ID),
                        new PcCase(BaseModel.DUMMY_ID));
            }
        };
    }

    @Override
    protected BasicDAO<Warehouse> makeWarehouseStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "warehouses";
            }

            @Override
            protected @NotNull List<? extends Warehouse> getStoredClasses() {
                return List.of(new Warehouse(BaseModel.DUMMY_ID));
            }
        };
    }

    @Override
    protected BasicDAO<Comment> makeCommentStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "comments";
            }

            @Override
            protected @NotNull List<? extends Comment> getStoredClasses() {
                return List.of(new Comment(BaseModel.DUMMY_ID));
            }
        };
    }

    @Override
    protected BasicDAO<Order> makeOrderStorage() {
        return new BasicDBDAO<>() {
            @Override
            protected @NotNull String getTableName() {
                return "orders";
            }

            @Override
            protected @NotNull List<? extends Order> getStoredClasses() {
                return List.of(new Order(BaseModel.DUMMY_ID));
            }
        };
    }

    @Override
    protected LoginController makeLoginController() {
        return new LocalLoginController(userStorage);
    }
}
