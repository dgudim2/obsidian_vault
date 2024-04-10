package org.kloud.daos.file;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.Warehouse;

/**
 * {@link BasicFileDAO} for {@link Warehouse Warehouses}
 */
public class FileWarehousesDAO extends BasicFileDAO<Warehouse> {
    @Override
    protected @NotNull String getFileName() {
        return "warehouses.dat";
    }
}
