package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.MotherboardFormFactor;

import java.util.List;
import java.util.Objects;

public class Motherboard extends HardwarePart {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motherboard that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(formFactor, that.formFactor) && Objects.equals(cpuSocketType, that.cpuSocketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), formFactor, cpuSocketType);
    }
}
