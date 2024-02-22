package org.kloud.model.product;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class Product {

    protected String name;
    protected String description;
    protected double price;
    protected Duration warranty;
    protected float rating;

    public Map<String, Class<?>> getFields() {
        var fields = new HashMap<String, Class<?>>(5);
        fields.put("Name", String.class);
        fields.put("Description", String.class);
        fields.put("Price", Double.class);
        fields.put("Warranty until", Duration.class);
        fields.put("Rating", Float.class);
        return fields;
    }
}
