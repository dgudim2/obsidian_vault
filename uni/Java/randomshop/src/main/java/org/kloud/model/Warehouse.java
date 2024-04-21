package org.kloud.model;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyField;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.model.product.Product;
import org.kloud.model.user.Manager;
import org.kloud.utils.Conf;
import org.kloud.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Warehouse extends BaseModel {

    @EqualsAndHashCode.Exclude
    public static final String NAME = "Warehouse";

    public final Field<String> address = new Field<>("Address", true, String.class, v -> Utils.testLength(v, 5, -1));

    public final Field<Integer> maxCapacity = new Field<>("Capacity", true, Integer.class, v -> Utils.testBounds(v, 100, -1));

    public final ForeignKeyField<Manager> assignedManager = new ForeignKeyField<>("Assigned manager", false,
            id -> {
                var user = Conf.getStorage()
                        .getUserStorage().getById(id);
                if (user instanceof Manager m) {
                    return m;
                }
                return null;
            },
            () -> Conf.getStorage()
                    .getUserStorage().getObjects().stream()
                    .filter(user -> user instanceof Manager)
                    .map(user -> (Manager) user)
                    .toList(), (manager, newManager) -> {
    });

    // TODO: Take warehouse capacity into account
    public final ForeignKeyListField<Product> products = new ForeignKeyListField<>("Products", false, true, () -> false,
            ids -> Conf.getStorage()
                    .getProductStorage().getWithFilter(product -> product.assignedWarehouse.get() == id),
            () -> Conf.getStorage()
                    .getProductStorage().getObjects(),
            (oldProducts, products) -> {
                for (var oldProduct : oldProducts) {
                    // NOTE: This is a dirty way to do it, should calculate difference instead
                    oldProduct.assignedWarehouse.set((long) -1);
                }
                for (var product : products) {
                    product.assignedWarehouse.set(id);
                }
            });

    public Warehouse() {
        super();
    }

    public Warehouse(long id) {
        super(id);
    }

    public boolean isFullCapacity() {
        return products.get().size() >= maxCapacity.get();
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        return new ArrayList<>(List.of(address, maxCapacity, assignedManager, products));
    }

    @Override
    public String isSafeToDelete() {
        var manager = assignedManager.getLinkedValue();
        if (manager != null) {
            return "Warehouse has a manager: " + manager.name;
        }
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        return address + " (" + products.get().size() + "/" + maxCapacity + " units)";
    }
}
