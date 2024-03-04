package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends BaseModel {
    @Override
    public @NotNull List<Field<?>> getFields() {
        return new ArrayList<>();
    }

    @Override
    protected @NotNull String toStringInternal() {
        return "NOT IMPLEMENTED";
    }
}
