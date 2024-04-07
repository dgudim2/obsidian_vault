package org.kloud.common.datatypes;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Base class for custom datatypes not present in java and/or in the database
 */
public abstract class CustomDatatype implements Serializable {
    public abstract String serializeToString();

    public abstract void deserializeFromString(@Nullable String data); // Ideally should still exist on the child classes but as a static method
}
