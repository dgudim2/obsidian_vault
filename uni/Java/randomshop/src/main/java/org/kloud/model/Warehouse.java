package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.common.ForeignKeyField;
import org.kloud.model.user.Manager;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends BaseModel {

    public static String NAME = "Warehouse";

    public final Field<String> address = new Field<>("Address", true, String.class, v -> Utils.testLength(v, 5, -1));

    public final Field<Integer> maxCapacity = new Field<>("Capacity", true, Integer.class, v -> Utils.testBounds(v, 100, -1));

    public final ForeignKeyField<Manager> assignedManager = new ForeignKeyField<>("Assigned manager", false,
            () -> ConfigurationSingleton.getInstance().storageBackend.get()
                    .getUserStorage().getObjects().stream().filter(user -> user instanceof Manager).map(user -> (Manager) user).toList(), manager -> {});


    public Warehouse() {
        super();
    }

    public Warehouse(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(address);
        fields.add(maxCapacity);
        fields.add(assignedManager);
        return fields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return address + " (max " + maxCapacity + " units)";
    }
}
