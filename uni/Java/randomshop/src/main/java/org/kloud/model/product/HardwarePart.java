package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Dimensions;
import org.kloud.common.Field;

import java.util.List;

public abstract class HardwarePart extends Product {
    protected final Field<Dimensions> dimens = new Field<>("Dimensions", true, Dimensions.class, __ -> "");

    @Override
    public @NotNull List<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(dimens);
        return superFields;
    }
}
