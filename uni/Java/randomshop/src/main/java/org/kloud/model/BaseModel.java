package org.kloud.model;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.Fields.Field;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

/**
 * vase class for all storable objects
 */
public abstract class BaseModel implements Serializable {

    // Id used in getStoredClasses, a dummy id
    public static final long DUMMY_ID = 333;

    public final long id;

    protected BaseModel(long id) {
        this.id = id;
    }

    protected BaseModel() {
        id = System.nanoTime();
    }

    @NotNull
    public abstract List<Field<?>> getFields();

    public List<? extends ColumnDescriptor<?>> getColumnDescriptors() {
        return getFields().stream().map(Field::getColumnDescriptor).toList();
    }

    public Map<String, Pair<Object, Class<?>>> getFieldMap() {
        var fields = getFields();
        var mapping = new HashMap<String, Pair<Object, Class<?>>>(fields.size());
        for (var field : fields) {
            mapping.put(field.name, new Pair<>(field.get(), field.klass));
        }
        return mapping;
    }

    public boolean isLatestVestionSaved() {
        for (var field : getFields()) {
            if (!field.isLatestVersionSaved()) {
                return false;
            }
        }
        return true;
    }

    public void markLatestVersionSaved() {
        for (var field : getFields()) {
            field.markLatestVersionSaved();
        }
    }

    public abstract String isSafeToDelete();

    public void postRead() {
        BaseModel cleanInstance;
        try {
            // NOTE: Calling the constructor with id is CRUCIAL because some
            // validators use the id and if not copied, it would be that of the cleanInstance
            // Another option would be co copy values from the current instance to the clean one and return the clean one
            cleanInstance = getClass().getDeclaredConstructor(long.class).newInstance(id);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        var fields = getFields();
        var cleanFields = cleanInstance.getFields();
        for (int i = 0; i < min(fields.size(), cleanFields.size()); i++) {
            fields.get(i).postRead(cleanFields.get(i));
        }
    }

    @Override
    public String toString() {
        return toStringInternal();
    }

    @NotNull
    protected abstract String toStringInternal();
}
