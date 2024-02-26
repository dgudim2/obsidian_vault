package org.kloud.model.product;

import org.kloud.common.Field;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public abstract class Product {

    protected final Field<String> name = new Field<>("Name", true, String.class, __ -> "");
    protected final Field<String> description = new Field<>("Description", true, String.class, __ -> "");
    protected final Field<Double> price = new Field<>("Price", true, Double.class, __ -> "");
    protected final Field<Duration> warranty = new Field<>("Warranty", true, Duration.class, __ -> "");
    protected final Field<Float> rating = new Field<>("Rating", true, Float.class, __ -> "");

    public Set<Field<?>> getFields() {
        Set<Field<?>> fields = new HashSet<>(5);
        fields.add(name);
        fields.add(description);
        fields.add(price);
        fields.add(warranty);
        fields.add(rating);
        return fields;
    }
}
