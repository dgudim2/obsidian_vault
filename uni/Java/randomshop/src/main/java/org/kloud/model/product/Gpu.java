package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.model.enums.GpuMemoryType;

import java.util.List;

public class Gpu extends HardwarePart {

    public static String NAME = "Gpu";

    protected final Field<Float> tdp = new Field<>("TDP", true, Float.class, __ -> "");
    protected final Field<Long> memoryMb = new Field<>("Memory (Mb)", true, Long.class, __ -> "");
    protected final Field<Integer> memoryBusWidthBytes = new Field<>("Memory bus width (bytes)", true, Integer.class, __ -> "");
    protected final Field<Integer> memoryClockMhz = new Field<>("Memory clock (MHz)", true, Integer.class, __ -> "");
    protected final Field<Integer> maxMemoryClockMhz = new Field<>("Max memory clock (MHz)", true, Integer.class, __ -> "");
    protected final Field<GpuMemoryType> memoryType = new Field<>("Memory type", true, GpuMemoryType.class, __ -> "");

    @Override
    public List<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(tdp);
        superFields.add(memoryMb);
        superFields.add(memoryBusWidthBytes);
        superFields.add(memoryClockMhz);
        superFields.add(maxMemoryClockMhz);
        superFields.add(memoryType);
        return superFields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name;
    }
}
