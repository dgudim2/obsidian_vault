package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.model.enums.GpuMemoryType;

import java.util.List;
import java.util.Objects;

import static org.kloud.utils.Utils.testBounds;

public class Gpu extends HardwarePart {

    public static final String NAME = "Gpu";

    protected final Field<Float> tdp = new Field<>("TDP (W)", true, Float.class, v -> testBounds(v, 1, -1));
    protected final Field<Long> memoryMb = new Field<>("Memory (Mb)", true, Long.class, v -> testBounds(v, 100, -1));
    protected final Field<Integer> memoryBusWidthBytes = new Field<>("Memory bus width (bytes)", true, Integer.class, v -> testBounds(v, 100, -1));
    protected final Field<Integer> memoryClockMhz = new Field<>("Memory clock (MHz)", true, Integer.class, v -> testBounds(v, 100, -1));
    protected final Field<Integer> maxMemoryClockMhz = new Field<>("Max memory clock (MHz)", true, Integer.class, v -> testBounds(v, 100, -1));
    protected final Field<GpuMemoryType> memoryType = new Field<>("Memory type", true, GpuMemoryType.class, __ -> "");

    public Gpu() {
        super();
    }

    public Gpu(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
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
        return NAME + ": " + name + " (" + tdp + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gpu gpu)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(tdp, gpu.tdp) && Objects.equals(memoryMb, gpu.memoryMb) && Objects.equals(memoryBusWidthBytes, gpu.memoryBusWidthBytes) && Objects.equals(memoryClockMhz, gpu.memoryClockMhz) && Objects.equals(maxMemoryClockMhz, gpu.maxMemoryClockMhz) && Objects.equals(memoryType, gpu.memoryType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tdp, memoryMb, memoryBusWidthBytes, memoryClockMhz, maxMemoryClockMhz, memoryType);
    }
}
