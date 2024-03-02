package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.EnumField;
import org.kloud.common.Field;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.MotherboardFormFactor;

import java.util.List;
import java.util.Objects;

public class Motherboard extends HardwarePart {

    public static String NAME = "Motherboard";
    protected final EnumField<MotherboardFormFactor> formFactor = new EnumField<>("Form-factor", true, MotherboardFormFactor.class, __ -> "");
    protected final EnumField<CpuSocketType> cpuSocketType = new EnumField<>("Socket type", true, CpuSocketType.class, __ -> "");

    @Override
    public List<Field<?>>getFields() {
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
