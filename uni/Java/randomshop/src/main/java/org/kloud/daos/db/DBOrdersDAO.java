package org.kloud.daos.db;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.model.Order;

import java.util.List;

/**
 * {@link BasicDBDAO} for {@link Order Orders}
 */
public class DBOrdersDAO extends BasicDBDAO<Order> {
    @Override
    protected @NotNull String getTableName() {
        return "orders";
    }

    @Override
    protected @NotNull List<? extends Order> getStoredClasses() {
        return List.of(new Order(BaseModel.DUMMY_ID));
    }
}