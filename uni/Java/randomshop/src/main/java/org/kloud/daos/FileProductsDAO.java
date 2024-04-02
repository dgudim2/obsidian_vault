package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;

public class FileProductsDAO extends BasicFileDAO<Product> {
    @Override
    @NotNull
    protected String getFilePath() {
        return "products.dat";
    }
}
