package org.kloud.model.product;

import org.kloud.common.Field;
import org.kloud.model.enums.MotherboardFormFactor;
import org.kloud.model.enums.SidePanelType;

import java.awt.*;
import java.util.Set;

public class PcCase extends HardwarePart {

    protected final Field<Color> color = new Field<>("Color", true, Color.class, __ -> "");
    protected final Field<MotherboardFormFactor> motherboardFormFactor = new Field<>("Form-factor", true, MotherboardFormFactor.class, __ -> "");
    protected final Field<SidePanelType> sidePanelType = new Field<>("Side-panel type", true, SidePanelType.class, __ -> "");

    @Override
    public Set<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(color);
        superFields.add(motherboardFormFactor);
        superFields.add(sidePanelType);
        return superFields;
    }
}
