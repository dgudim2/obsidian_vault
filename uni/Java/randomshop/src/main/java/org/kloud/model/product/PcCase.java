package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.EnumField;
import org.kloud.common.Field;
import org.kloud.model.enums.MotherboardFormFactor;
import org.kloud.model.enums.SidePanelType;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PcCase extends HardwarePart {

    public static String NAME = "Pc case";
    protected final Field<Color> color = new Field<>("Color", true, Color.class, __ -> "");
    protected final EnumField<MotherboardFormFactor> motherboardFormFactor = new EnumField<>("Form-factor", true, MotherboardFormFactor.class);
    protected final EnumField<SidePanelType> sidePanelType = new EnumField<>("Side-panel type", true, SidePanelType.class);

    @Override
    public @NotNull List<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(color);
        superFields.add(motherboardFormFactor);
        superFields.add(sidePanelType);
        return superFields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " (" + sidePanelType + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PcCase pcCase)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(color, pcCase.color) && Objects.equals(motherboardFormFactor, pcCase.motherboardFormFactor) && Objects.equals(sidePanelType, pcCase.sidePanelType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, motherboardFormFactor, sidePanelType);
    }
}
