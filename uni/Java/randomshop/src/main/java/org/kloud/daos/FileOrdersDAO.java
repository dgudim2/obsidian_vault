package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.Order;

/**
 * {@link BasicFileDAO} for {@link Order Orders}
 */
public class FileOrdersDAO extends BasicFileDAO<Order> {
    @Override
    @NotNull
    protected String getFileName() {
        return "orders.dat";
    }
}
