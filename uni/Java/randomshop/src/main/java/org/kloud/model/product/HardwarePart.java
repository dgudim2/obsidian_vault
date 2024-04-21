package org.kloud.model.product;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.datatypes.Dimensions;
import org.kloud.common.fields.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public abstract class HardwarePart extends Product {
    protected final Field<Dimensions> dimens = new Field<>("Dimensions", true, Dimensions.class, __ -> "");

    public HardwarePart() {
        super();
    }

    public HardwarePart(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(dimens);
        return superFields;
    }
}
