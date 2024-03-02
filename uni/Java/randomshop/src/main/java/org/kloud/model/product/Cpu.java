package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.EnumField;
import org.kloud.common.Field;
import org.kloud.model.enums.CpuArchitecture;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.Manufacturer;

import java.util.List;
import java.util.Objects;

public class Cpu extends HardwarePart {

    public static String NAME = "Cpu";

    protected final Field<Float> tdp = new Field<>("TDP (W)", true, Float.class, __ -> "");

    protected final EnumField<Manufacturer> manufacturer = new EnumField<>("Manufacturer", true, Manufacturer.class, __ -> "");

    protected final EnumField<CpuSocketType> socketType = new EnumField<>("Socket type", true, CpuSocketType.class, __ -> "");

    protected final EnumField<CpuArchitecture> architecture = new EnumField<>("Architechture", true, CpuArchitecture.class, __ -> "");

    protected final Field<Long> clockFrequencyMhz = new Field<>("Clock (MHz)", true, Long.class, __ -> "");

    protected final Field<Integer> numCores = new Field<>("Cores", true, Integer.class, __ -> "");

    protected final Field<Integer> cacheSizeMb = new Field<>("Cache size (Mb)", true, Integer.class, __ -> "");

    protected final Field<Integer> techProcessNm = new Field<>("Tech process (nm)", true, Integer.class, __ -> "");

    protected final Field<Boolean> hasIGpu = new Field<>("Has IGpu", true, Boolean.class, __ -> "");

    protected final Field<Long> maxRamCapacityMb = new Field<>("Max ram capacity (mb)", true, Long.class, __ -> "");

    @Override
    public List<Field<?>> getFields() {
        var superFields = super.getFields();
        superFields.add(tdp);
        superFields.add(manufacturer);
        superFields.add(socketType);
        superFields.add(architecture);
        superFields.add(clockFrequencyMhz);
        superFields.add(numCores);
        superFields.add(cacheSizeMb);
        superFields.add(techProcessNm);
        superFields.add(hasIGpu);
        superFields.add(maxRamCapacityMb);
        return superFields;
    }

    @Override
    protected @NotNull String toStringInternal() {
        return NAME + ": " + name + " (" + socketType + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpu cpu)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(tdp, cpu.tdp) && Objects.equals(manufacturer, cpu.manufacturer) && Objects.equals(socketType, cpu.socketType) && Objects.equals(architecture, cpu.architecture) && Objects.equals(clockFrequencyMhz, cpu.clockFrequencyMhz) && Objects.equals(numCores, cpu.numCores) && Objects.equals(cacheSizeMb, cpu.cacheSizeMb) && Objects.equals(techProcessNm, cpu.techProcessNm) && Objects.equals(hasIGpu, cpu.hasIGpu) && Objects.equals(maxRamCapacityMb, cpu.maxRamCapacityMb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tdp, manufacturer, socketType, architecture, clockFrequencyMhz, numCores, cacheSizeMb, techProcessNm, hasIGpu, maxRamCapacityMb);
    }
}
