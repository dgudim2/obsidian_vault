package org.kloud.daos;

import org.jetbrains.annotations.NotNull;

/**
 * {@link FileProductsDAO} with a different file name
 */
public class FileOrderedProductsDAO extends FileProductsDAO {
    @Override
    protected @NotNull String getFileName() {
        return "ordered_" + super.getFileName();
    }
}
