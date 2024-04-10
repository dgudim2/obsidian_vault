package org.kloud.daos.file;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;

/**
 * {@link BasicFileDAO} for {@link Product Products}
 */
public class FileProductsDAO extends BasicFileDAO<Product> {
    @Override
    @NotNull
    protected String getFileName() {
        return "products.dat";
    }
}
