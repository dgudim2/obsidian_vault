package org.kloud.model.product;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.model.enums.MotherboardFormFactor;
import org.kloud.model.enums.SidePanelType;

import java.awt.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class PcCase extends HardwarePart {

    @EqualsAndHashCode.Exclude
    public static final String NAME = "Pc case";
    protected final Field<Color> color = new Field<>("Color", true, Color.class, __ -> "");
    protected final Field<MotherboardFormFactor> motherboardFormFactor = new Field<>("Form-factor", true, MotherboardFormFactor.class, __ -> "");
    protected final Field<SidePanelType> sidePanelType = new Field<>("Side-panel type", true, SidePanelType.class, __ -> "");

    public PcCase() {
        super();
    }

    public PcCase(long id) {
        super(id);
    }

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
}
