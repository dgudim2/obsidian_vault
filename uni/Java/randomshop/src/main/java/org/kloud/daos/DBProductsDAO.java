package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.*;

import java.util.List;

public class DBProductsDAO extends BasicDBDAO<Product> {
    @Override
    protected @NotNull String getTableName() {
        return "products";
    }

    @Override
    protected @NotNull List<? extends Product> getStoredClasses() {
        return List.of(new Cpu(), new Gpu(), new Motherboard(), new PcCase());
    }
}
