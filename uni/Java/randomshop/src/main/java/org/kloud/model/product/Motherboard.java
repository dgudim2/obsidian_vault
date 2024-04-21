package org.kloud.model.product;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.MotherboardFormFactor;

import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Motherboard extends HardwarePart {

    @EqualsAndHashCode.Exclude
    public static final String NAME = "Motherboard";
    protected final Field<MotherboardFormFactor> formFactor = new Field<>("Form-factor", true, MotherboardFormFactor.class, __ -> "");
    protected final Field<CpuSocketType> cpuSocketType = new Field<>("Socket type", true, CpuSocketType.class, __ -> "");

    public Motherboard() {
        super();
    }

    public Motherboard(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>>getFields() {
        var superFields = super.getFields();
        superFields.add(formFactor);
        superFields.add(cpuSocketType);
        return superFields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " (" + formFactor + ")";
    }
}
