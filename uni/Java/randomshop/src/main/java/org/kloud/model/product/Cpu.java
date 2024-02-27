package org.kloud.model.product;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.model.enums.CpuArchitecture;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.Manufacturer;

import java.util.List;

public class Cpu extends HardwarePart {

    public static String NAME = "Cpu";

    protected final Field<Float> tdp = new Field<>("TDP", true, Float.class, __ -> "");

    protected final Field<Manufacturer> manufacturer = new Field<>("Manufacturer", true, Manufacturer.class, __ -> "");

    protected final Field<CpuSocketType> socketType = new Field<>("Socket type", true, CpuSocketType.class, __ -> "");

    protected final Field<CpuArchitecture> architecture = new Field<>("Architechture", true, CpuArchitecture.class, __ -> "");

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
        return NAME + ": " + name;
    }
}
