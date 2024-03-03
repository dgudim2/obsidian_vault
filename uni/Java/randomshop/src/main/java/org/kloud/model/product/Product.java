package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.min;

public abstract class Product implements Serializable {

    public static final List<Class<? extends Product>> PRODUCTS = List.of(Cpu.class, Gpu.class, Motherboard.class, PcCase.class);

    public final long id;

    protected final Field<String> name = new Field<>("Name", true, String.class, name -> Utils.testLength(name, 2, 30));
    protected final Field<String> description = new Field<>("Description", true, String.class, desc -> Utils.testLength(desc, 2, 300));
    protected final Field<Double> price = new Field<>("Price", true, Double.class, __ -> "");
    protected final Field<LocalDate> warranty = new Field<>("Warranty", true, LocalDate.class, date -> {
        var now = LocalDate.now();
        if (date.equals(now) || date.isBefore(now)) {
            return "Warranty should be > today";
        }
        return "";
    });
    protected final Field<Float> rating = new Field<>("Rating", true, Float.class, v -> Utils.testBounds(v, 0, 10));

    protected Product() {
        id = System.nanoTime();
    }

    public List<Field<?>> getFields() {
        List<Field<?>> fields = new ArrayList<>(5);
        fields.add(name);
        fields.add(description);
        fields.add(price);
        fields.add(warranty);
        fields.add(rating);
        return fields;
    }

    public void postDeserialize() {
        Product cleanInstance;
        try {
            cleanInstance = getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        var fields = getFields();
        var cleanFields = cleanInstance.getFields();
        for (int i = 0; i < min(fields.size(), cleanFields.size()); i++) {
            var field = fields.get(i);
            field.postDeserialize(cleanFields.get(i));
            System.out.println("postDeserialize for " + field.name + " (" + field.get() + ")");
        }
    }

    @Override
    public String toString() {
        return toStringInternal();
    }

    protected abstract @NotNull String toStringInternal();

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
