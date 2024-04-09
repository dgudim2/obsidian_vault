package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.model.product.*;

import java.util.List;

/**
 * {@link BasicDBDAO} for {@link Product Products}
 */
public class DBProductsDAO extends BasicDBDAO<Product> {
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
}
