package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Fields.Field;
import org.kloud.common.Fields.ForeignKeyField;
import org.kloud.common.Fields.ForeignKeyListField;
import org.kloud.common.Fields.RatingField;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;
import org.kloud.model.Warehouse;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Product extends BaseModel {

    public static final List<Class<? extends Product>> PRODUCTS = List.of(Cpu.class, Gpu.class, Motherboard.class, PcCase.class);

    public final Field<String> name = new Field<>("Name", true, String.class, name -> Utils.testLength(name, 2, 30));
    public final Field<String> description = new Field<>("Description", true, String.class, desc -> Utils.testLength(desc, 2, 300));
    public final Field<Double> price = new Field<>("Price", true, Double.class, v -> Utils.testBounds(v, 0.1, -1));
    public final Field<LocalDate> warranty = new Field<>("Warranty", true, LocalDate.class, date -> {
        var now = LocalDate.now();
        if (date.equals(now) || date.isBefore(now)) {
            return "Warranty should be > today";
        }
        return "";
    });
    public final RatingField rating = new RatingField("Rating");

    public final ForeignKeyField<Warehouse> assignedWarehouse = new ForeignKeyField<>("Assigned warehouse", true,
            id -> ConfigurationSingleton.getStorage()
                    .getWarehouseStorage().getById(id),
            () -> ConfigurationSingleton.getStorage()
                    .getWarehouseStorage().getObjects()
                    .stream()
                    .filter(warehouse -> !warehouse.isFullCapacity())
                    .toList(), o -> {
    });

    public final ForeignKeyListField<Comment> comments = new ForeignKeyListField<>("Comments", false, false, true,
            ids -> ConfigurationSingleton.getStorage().getCommentStorage().getByIds(ids), List::of, (comments1, comments2) -> {
    });

    public Product() {
        super();
    }

    public Product(long id) {
        super(id);
    }

    @Override
    public String isSafeToDelete() {
        return "";
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public @NotNull List<Field<?>> getFields() {
        List<Field<?>> fields = new ArrayList<>(5);
        fields.add(name);
        fields.add(description);
        fields.add(price);
        fields.add(warranty);
        fields.add(rating);
        fields.add(assignedWarehouse);
        fields.add(comments);
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(warranty, product.warranty) && Objects.equals(rating, product.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, warranty, rating);
    }
}
