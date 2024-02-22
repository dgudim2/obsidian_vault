package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.product.Product;

import java.util.List;

public abstract class ProductsDAO {

    @Nullable
    public abstract List<Product> readProducts();

    public abstract boolean writeProducts(@NotNull List<Product> users);

}
