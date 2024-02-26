package org.kloud.model.product;

import org.kloud.common.Field;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.MotherboardFormFactor;

import java.util.Set;

public class Motherboard extends HardwarePart {
    protected final Field<MotherboardFormFactor> formFactor = new Field<>("Form-factor", true, MotherboardFormFactor.class, __ -> "");
    protected final Field<CpuSocketType> cpuSocketType = new Field<>("Socket type", true, CpuSocketType.class, __ -> "");

    @Override
    public Set<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(formFactor);
        superFields.add(cpuSocketType);
        return superFields;
    }
}
