package org.kloud.daos.db;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.model.Warehouse;

import java.util.List;

/**
 * {@link BasicDBDAO} for {@link Warehouse Warehouses}
 */
public class DBWarehousesDAO extends BasicDBDAO<Warehouse> {
    @Override
    protected @NotNull String getTableName() {
        return "warehouses";
    }

    @Override
    protected @NotNull List<? extends Warehouse> getStoredClasses() {
        return List.of(new Warehouse(BaseModel.DUMMY_ID));
    }
}
