package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.product.Product;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.readObject;
import static org.kloud.utils.Utils.writeObject;

public class FileProductsDAO extends ProductsDAO {

    private static final String FILE_PATH = "products.dat";

    @Override
    public @Nullable List<Product> readProducts() {
        return readObject(FILE_PATH, new ArrayList<>());
    }

    @Override
    public boolean writeProducts(@NotNull List<Product> products) {
        return writeObject(products, FILE_PATH);
    }
}
