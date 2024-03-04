package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.Warehouse;

public class FileWarehousesDAO extends BasicFileDAO<Warehouse> {
    @Override
    protected @NotNull String getFilePath() {
        return "warehouses.dat";
    }
}
