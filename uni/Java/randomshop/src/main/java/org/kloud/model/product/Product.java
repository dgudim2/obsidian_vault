package org.kloud.model.product;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyField;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.common.fields.RatingField;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;
import org.kloud.model.Warehouse;
import org.kloud.utils.Conf;
import org.kloud.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public abstract class Product extends BaseModel {

    @EqualsAndHashCode.Exclude
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
            id -> Conf.getStorage()
                    .getWarehouseStorage().getById(id),
            () -> Conf.getStorage()
                    .getWarehouseStorage().getWithFilter(warehouse -> !warehouse.isFullCapacity()), (o, o1) -> {
    });

    public final ForeignKeyListField<Comment> comments = new ForeignKeyListField<>("Comments", false, false, () -> true,
            ids -> Conf.getStorage().getCommentStorage().getByIds(ids), List::of, (comments1, comments2) -> {
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
        return new ArrayList<>(List.of(name, description, price, warranty, rating, assignedWarehouse, comments));
    }
}
