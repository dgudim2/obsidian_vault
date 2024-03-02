package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;

import java.util.Collections;
import java.util.List;

public abstract class ProductsDAO {

    protected final List<Product> products;

    protected int lastSavedHash = -1;

    ProductsDAO() {
        products = readProducts();
    }

    @NotNull
    protected abstract List<Product> readProducts();

    @NotNull
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public abstract boolean addProduct(@NotNull Product product);

    public abstract boolean removeProduct(@NotNull Product product);

    public boolean isLatestVersionSaved() {
        System.out.println("isLatestVersionSaved for ProductDAO: " + lastSavedHash + ", " + products.hashCode());
        System.out.println(products);
        return lastSavedHash == products.hashCode();
    }
}
