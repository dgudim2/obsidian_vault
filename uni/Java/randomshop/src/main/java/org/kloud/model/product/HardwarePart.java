package org.kloud.model.product;

import org.kloud.common.Dimensions;
import org.kloud.common.Field;

import java.util.Set;

public abstract class HardwarePart extends Product {
    protected final Field<Dimensions> dimens = new Field<>("Dimensions", true, Dimensions.class, __ -> "");

    @Override
    public Set<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(dimens);
        return superFields;
    }
}
