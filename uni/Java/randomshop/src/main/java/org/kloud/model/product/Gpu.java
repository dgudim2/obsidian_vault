package org.kloud.model.product;

import org.kloud.model.enums.GpuMemoryType;
import org.kloud.model.enums.GpuOutputType;

import java.util.Map;

public class Gpu extends HardwarePart {

    protected float tdp;
    protected long memoryMb;
    protected int memoryBusWidthBytes;
    protected int memoryClockMhz;
    protected int maxMemoryClockMhz;
    protected GpuMemoryType memoryType;
    protected Map<GpuOutputType, Integer> outputCapabilities;
}
