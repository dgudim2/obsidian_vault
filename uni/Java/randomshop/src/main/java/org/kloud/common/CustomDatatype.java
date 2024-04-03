package org.kloud.common;

import java.io.Serializable;

public abstract class CustomDatatype implements Serializable {
    public abstract String serializeToString();

    public abstract void deserializeFromString(String data); // Ideally should still exist on the child classes but as a static method
}
