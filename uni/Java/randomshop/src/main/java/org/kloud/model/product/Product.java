package org.kloud.model.product;

import java.math.BigDecimal;
import java.time.Duration;

public abstract class Product {

    protected String name;
    protected String description;
    protected double price;
    protected Duration warranty;
    protected float rating;

}
