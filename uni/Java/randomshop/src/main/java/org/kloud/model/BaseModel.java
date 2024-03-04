package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static java.lang.Math.min;

public abstract class BaseModel implements Serializable {

    public final long id;

    protected BaseModel() {
        id = System.nanoTime();
    }

    @NotNull
    public abstract List<Field<?>> getFields();

    public void postRead() {
        BaseModel cleanInstance;
        try {
            cleanInstance = getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        var fields = getFields();
        var cleanFields = cleanInstance.getFields();
        for (int i = 0; i < min(fields.size(), cleanFields.size()); i++) {
            var field = fields.get(i);
            field.postRead(cleanFields.get(i));
        }
    }

    @Override
    public String toString() {
        return toStringInternal();
    }

    @NotNull
    protected abstract String toStringInternal();
}
