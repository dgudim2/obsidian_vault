package org.kloud.model.user;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.enums.UserCapability;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.model.Order;
import org.kloud.model.Warehouse;
import org.kloud.model.enums.OrderStatus;
import org.kloud.utils.Conf;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Manager extends User {

    @EqualsAndHashCode.Exclude
    public static final String NAME = "Manager";

    public final Field<Boolean> isAdmin = new Field<>("Admin", false, false, Boolean.class, __ -> "");
    public final Field<Boolean> isSuperAdmin = new Field<>("Super admin", false, false, Boolean.class, __ -> "");

    public final ForeignKeyListField<Order> assignedOrders = new ForeignKeyListField<>("Orders", false, true, () -> false,
            ids -> Conf.getStorage()
                    .getOrderStorage().getWithFilter(order -> order.assignedManager.get() == id),
            () -> Conf.getStorage()
                    .getOrderStorage().getWithFilter(order ->
                            order.status.get() != OrderStatus.CART &&
                                    order.status.get() != OrderStatus.CANCELLED &&
                                    order.status.get() != OrderStatus.DELIVERED),
            (oldOrders, orders) -> {
                for (var oldOrder : oldOrders) {
                    // NOTE: This is a dirty way to do it, should calculate difference instead
                    oldOrder.assignedManager.set((long) -1);
                    Conf.getStorage().getOrderStorage().addOrUpdateObject(oldOrder);
                }
                for (var order : orders) {
                    order.assignedManager.set(id);
                    Conf.getStorage().getOrderStorage().addOrUpdateObject(order);
                }
            });

    public final ForeignKeyListField<Warehouse> linkedWarehouses = new ForeignKeyListField<>("Warehouses", false, true, () -> false,
            ids -> Conf.getStorage()
                    .getWarehouseStorage().getWithFilter(warehouse -> warehouse.assignedManager.get() == id),
            () -> Conf.getStorage().getWarehouseStorage().getObjects(),
            (oldWarehouses, warehouses) -> {
                for (var oldWarehouse : oldWarehouses) {
                    // NOTE: This is a dirty way to do it, should calculate difference instead
                    oldWarehouse.assignedManager.set((long) -1);
                    Conf.getStorage().getWarehouseStorage().addOrUpdateObject(oldWarehouse);
                }
                for (var warehouse : warehouses) {
                    warehouse.assignedManager.set(id);
                    Conf.getStorage().getWarehouseStorage().addOrUpdateObject(warehouse);
                }
            });

    // For serialization, don't remove
    public Manager() {
        super();
    }

    public Manager(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        var fields = super.getFields();
        fields.add(isAdmin);
        fields.add(isSuperAdmin);
        fields.add(linkedWarehouses);
        fields.add(assignedOrders);
        return fields;
    }

    @Override
    public Set<UserCapability> getUserCaps() {
        var caps = super.getUserCaps();
        caps.add(UserCapability.READ_CUSTOMERS);
        caps.add(UserCapability.READ_MANAGERS);
        caps.add(UserCapability.WRITE_PRODUCTS);
        caps.add(UserCapability.DELETE_ORDERS);
        caps.add(UserCapability.WRITE_CUSTOMERS);
        caps.add(UserCapability.RW_SELF_WAREHOUSES);
        caps.add(UserCapability.READ_OTHER_WAREHOUSES);
        caps.add(UserCapability.RW_SELF_ASSIGNED_ORDERS);
        if (isAdmin.get() || isSuperAdmin.get()) {
            caps.add(UserCapability.READ_OTHER_ORDERS);
            caps.add(UserCapability.READ_OTHER_ASSIGNED_ORDERS);
            caps.add(UserCapability.READ_ADMINS);
            caps.add(UserCapability.WRITE_MANAGERS);
            caps.add(UserCapability.WRITE_OTHER_WAREHOUSES);
            caps.add(UserCapability.WRITE_OTHER_COMMENTS);
            caps.add(UserCapability.CHANGE_ADMIN_PASSWORD);
            caps.add(UserCapability.CHANGE_CUSTOMER_PASSWORD);
            if (isSuperAdmin.get()) {
                caps.add(UserCapability.WRITE_OTHER_ORDERS);
                caps.add(UserCapability.WRITE_OTHER_ASSIGNED_ORDERS);
                caps.add(UserCapability.WRITE_ADMINS);
            }
        }
        return caps;
    }

    @Override
    public String isSafeToDelete() {
        if (!linkedWarehouses.get().isEmpty()) {
            return "Manager has " + linkedWarehouses.get().size() + " linked warehouses";
        }
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " " + surname;
    }
}
