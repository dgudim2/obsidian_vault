package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Fields.Field;
import org.kloud.common.Fields.ForeignKeyField;
import org.kloud.common.Fields.ForeignKeyListField;
import org.kloud.model.enums.OrderStatus;
import org.kloud.model.product.Product;
import org.kloud.model.user.User;
import org.kloud.utils.ConfigurationSingleton;

import java.util.List;

public class Order extends BaseModel {

    public final Field<OrderStatus> orderStatus = new Field<>("Status", OrderStatus.CART, true, OrderStatus.class, __ -> "");

    public final ForeignKeyField<User> orderedByUser = new ForeignKeyField<>("Ordered by", true,
            id -> ConfigurationSingleton.getStorage().getUserStorage().getById(id),
            () -> ConfigurationSingleton.getStorage().getUserStorage().getObjects(),
            user -> {
            });

    public final ForeignKeyListField<Product> orderedProducts = new ForeignKeyListField<>("Ordered by", false, false,
            ids -> ConfigurationSingleton.getStorage().getProductStorage().getByIds(ids),
            () -> ConfigurationSingleton.getStorage().getProductStorage().getObjects(), (products1, products2) -> {

    });

    public Order() {
        super();
    }

    public Order(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        return List.of(orderStatus, orderedByUser, orderedProducts);
    }

    @Override
    public String isSafeToDelete() {
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        return "";
    }
}
