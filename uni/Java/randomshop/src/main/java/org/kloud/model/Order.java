package org.kloud.model;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyField;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.model.enums.OrderStatus;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.model.user.User;
import org.kloud.utils.Conf;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Order extends BaseModel {

    // TODO: This field is actually required, this is just for the filtering ui
    public final Field<OrderStatus> status = new Field<>("Status", false, OrderStatus.class, __ -> "");

    // TODO: This field is actually required, this is just for the filtering ui
    public final ForeignKeyField<User> orderedByUser = new ForeignKeyField<>("Ordered by", false,
            id -> Conf.getStorage().getUserStorage().getById(id),
            () -> Conf.getStorage().getUserStorage().getObjects(),
            (user, newUser) -> {
            });

    public final ForeignKeyListField<Product> orderedProducts = new ForeignKeyListField<>("Products", true, false, () -> true,
            ids -> Conf.getStorage().getOrderedProductStorage().getByIds(ids),
            List::of, (products1, products2) -> {

    });

    // TODO: Extract common fields.
    public final ForeignKeyField<Manager> assignedManager = new ForeignKeyField<>("Assigned manager", false, () -> false,
            id -> {
                var user = Conf.getStorage()
                        .getUserStorage().getById(id);
                if (user instanceof Manager m) {
                    return m;
                }
                return null;
            },
            // NOTE: Using stream apis of small arrays is meh, investigate performance
            () -> Conf.getStorage()
                    .getUserStorage().getObjects().stream()
                    .filter(user -> user instanceof Manager)
                    .map(user -> (Manager) user)
                    .toList(), (manager, newManager) -> {
    });

    public final ForeignKeyListField<Comment> comments = new ForeignKeyListField<>("Comments", false, false, () -> true,
            ids -> Conf.getStorage().getCommentStorage().getByIds(ids), List::of, (comments1, comments2) -> {
    });

    public Order() {
        super();
    }

    public Order(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        return new ArrayList<>(List.of(status, orderedByUser, orderedProducts, assignedManager, comments));
    }

    @Override
    public String isSafeToDelete() {
        if (!orderedProducts.get().isEmpty()) {
            return "Order has " + orderedProducts.get().size() + " products";
        }
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        var strId = String.valueOf(id);
        return "Order " + (strId.substring(0, 2)) + ".." + (strId.substring(strId.length() - 2)) +
                " (" + status.get() + ") " + orderedProducts.get().size() + " product(s)";
    }
}
