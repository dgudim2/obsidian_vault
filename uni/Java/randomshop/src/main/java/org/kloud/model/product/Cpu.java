package org.kloud.model.product;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.model.enums.CpuArchitecture;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.Manufacturer;

import java.util.List;

import static org.kloud.utils.Utils.testBounds;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Cpu extends HardwarePart {

    @EqualsAndHashCode.Exclude
    public static final String NAME = "Cpu";

    protected final Field<Float> tdp = new Field<>("TDP (W)", true, Float.class, v -> testBounds(v, 1, -1));
    protected final Field<Manufacturer> manufacturer = new Field<>("Manufacturer", true, Manufacturer.class, __ -> "");
    protected final Field<CpuSocketType> socketType = new Field<>("Socket type", true, CpuSocketType.class, __ -> "");
    protected final Field<CpuArchitecture> architecture = new Field<>("Architechture", true, CpuArchitecture.class, __ -> "");
    protected final Field<Long> clockFrequencyMhz = new Field<>("Clock (MHz)", true, Long.class, v -> testBounds(v, 100, -1));
    protected final Field<Integer> numCores = new Field<>("Cores", true, Integer.class, v -> testBounds(v, 1, -1));
    protected final Field<Float> cacheSizeMb = new Field<>("Cache size (Mb)", true, Float.class, v -> testBounds(v, 1, -1));
    protected final Field<Integer> techProcessNm = new Field<>("Tech process (nm)", true, Integer.class, v -> testBounds(v, 1, -1));
    protected final Field<Boolean> hasIGpu = new Field<>("Has IGpu", true, Boolean.class, __ -> "");
    protected final Field<Long> maxRamCapacityMb = new Field<>("Max ram capacity (mb)", true, Long.class, v -> testBounds(v, 10, -1));

    public Cpu() {
        super();
    }

    public Cpu(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
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
}
