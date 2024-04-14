package org.kloud.daos.db;

import org.jetbrains.annotations.NotNull;

/**
 * {@link DBProductsDAO} with a different table name
 */
public class DBOrderedProductsDao extends DBProductsDAO {
    @Override
    protected @NotNull String getTableName() {
        return "ordered_" + super.getTableName();
    }
}
