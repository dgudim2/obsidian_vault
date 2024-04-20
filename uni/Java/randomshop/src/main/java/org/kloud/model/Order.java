package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyField;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.model.enums.OrderStatus;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.utils.ConfigurationSingleton;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    public final Field<OrderStatus> orderStatus = new Field<>("Status", OrderStatus.CART, true, OrderStatus.class, __ -> "");

    public final ForeignKeyField<User> orderedByUser = new ForeignKeyField<>("Ordered by", true,
            id -> ConfigurationSingleton.getStorage().getUserStorage().getById(id),
            List::of,
            (user, newUser) -> {
            });

    public final ForeignKeyListField<Product> orderedProducts = new ForeignKeyListField<>("Products",
            ids -> ConfigurationSingleton.getStorage().getOrderedProductStorage().getByIds(ids),
            List::of, (products1, products2) -> {

    });

    // TODO: Extract common fields.
    public final ForeignKeyField<Manager> assignedManager = new ForeignKeyField<>("Assigned manager", false,
            id -> {
                var user = ConfigurationSingleton.getStorage()
                        .getUserStorage().getById(id);
                if (user instanceof Manager m) {
                    return m;
                }
                return null;
            },
            // NOTE: Using stream apis of small arrays is meh, investigate performance
            () -> ConfigurationSingleton.getStorage()
                    .getUserStorage().getObjects().stream()
                    .filter(user -> user instanceof Manager)
                    .map(user -> (Manager) user)
                    .toList(), (manager, newManager) -> {
    });

    public Order() {
        super();
    }

    public Order(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        return new ArrayList<>(List.of(orderStatus, orderedByUser, orderedProducts, assignedManager));
    }

    @Override
    public String isSafeToDelete() {
        if(!orderedProducts.get().isEmpty()) {
            return "Order has " + orderedProducts.get().size() + " products";
        }
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        var strId = String.valueOf(id);
        return "Order " + (strId.substring(0, 2)) + ".." + (strId.substring(strId.length() - 2)) +
                " (" + orderStatus.get() + ") " + orderedProducts.get().size() + " products";
    }
}
