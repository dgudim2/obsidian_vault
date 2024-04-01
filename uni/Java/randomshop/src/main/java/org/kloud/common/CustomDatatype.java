package org.kloud.common;

public abstract class CustomDatatype {
    public abstract String serializeToString();
    public abstract void deserializeFromString(String data); // Ideally should still exist on the child classes but as a static method
}
