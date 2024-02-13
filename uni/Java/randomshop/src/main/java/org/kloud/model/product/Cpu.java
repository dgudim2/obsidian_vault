package org.kloud.model.product;

import org.kloud.model.enums.CpuArchitecture;
import org.kloud.model.enums.CpuSocketType;
import org.kloud.model.enums.Manufacturer;

import java.util.List;

public class Cpu extends HardwarePart {
    protected float tdp;
    protected Manufacturer manufacturer;
    protected CpuSocketType socketType;
    protected CpuArchitecture architecture;
    protected long clockFrequencyMhz;
    protected int numCores;
    protected int cacheSizeMb;
    protected int techProcessNm;
    protected boolean hasIGpu;
    protected boolean maxRamCapacityMb;
    protected List<String> flags;

}
