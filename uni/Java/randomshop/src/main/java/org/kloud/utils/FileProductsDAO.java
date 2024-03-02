package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.readObject;
import static org.kloud.utils.Utils.writeObject;

public class FileProductsDAO extends ProductsDAO {

    private static final String FILE_PATH = "products.dat";

    @Override
    protected @NotNull List<Product> readProducts() {
        ArrayList<Product> r_products = readObject(FILE_PATH, new ArrayList<>());
        for (var product : r_products) {
            product.postDeserialize();
        }
        lastSavedHash = r_products.hashCode();
        return r_products;
    }

    @Override
    public boolean addProduct(@NotNull Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
        return writeProducts();
    }

    @Override
    public boolean removeProduct(@NotNull Product product) {
        var removed = products.remove(product);
        if (removed) {
            return writeProducts();
        }
        return false;
    }

    private boolean writeProducts() {
        lastSavedHash = products.hashCode();
        return writeObject(products, FILE_PATH);
    }
}
