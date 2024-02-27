package org.kloud.model.product;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.common.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Product implements Serializable {

    public static final List<Class<? extends Product>> PRODUCTS = List.of(Cpu.class, Gpu.class, Motherboard.class, PcCase.class);

    protected final Field<String> name = new Field<>("Name", true, String.class, __ -> "");
    protected final Field<String> description = new Field<>("Description", true, String.class, __ -> "");
    protected final Field<Double> price = new Field<>("Price", true, Double.class, __ -> "");
    protected final Field<LocalDate> warranty = new Field<>("Warranty", true, LocalDate.class, __ -> "");
    protected final Field<Float> rating = new Field<>("Rating", true, Float.class, __ -> "");

    public List<Field<?>> getFields() {
        List<Field<?>> fields = new ArrayList<>(5);
        fields.add(name);
        fields.add(description);
        fields.add(price);
        fields.add(warranty);
        fields.add(rating);
        return fields;
    }

    @Override
    public String toString() {
        return toStringInternal();
    }

    protected abstract @NotNull String toStringInternal();

    @Nullable
    public Pair<String, Field<?>> getInvalidField() {
        var fields = getFields();
        for (var field : fields) {
            var validationResult = field.validate();
            if (validationResult != null) {
                return new Pair<>(validationResult, field);
            }
        }
        return null;
    }
}
