package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.utils.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class BasicDAO<T extends BaseModel> {
    protected List<T> objects;

    protected int lastSavedHash = -1;

    @NotNull
    protected List<T> readObjects() {
        List<T> r_objects = readObjectsInternal();
        for (var product : r_objects) {
            product.postRead();
        }
        lastSavedHash = r_objects.hashCode();
        return r_objects;
    }

    @NotNull
    protected abstract List<T> readObjectsInternal();

    protected abstract boolean writeObjectsInternal();

    protected boolean writeObjects() {
        lastSavedHash = objects.hashCode();
        return writeObjectsInternal();
    }

    @NotNull
    public List<T> getObjects() {
        if(objects == null) {
            objects = readObjects();
        }
        return Collections.unmodifiableList(objects);
    }

    public boolean addOrUpdateObject(@NotNull T object) {
        var existingObject = objects.stream().filter(o -> o.id == object.id).findFirst();
        if (existingObject.isPresent()) {
            if(Objects.equals(existingObject.get(), object)) {
                Logger.warn("Unnecessary call to addOrUpdateObject (the same object already exists)");
                return false;
            }
            objects.remove(existingObject.get());
        }
        objects.add(object);
        return writeObjects();
    }

    public boolean removeObject(@NotNull T object) {
        var removed = objects.remove(object);
        if (removed) {
            return writeObjects();
        }
        return false;
    }

    public boolean isLatestVersionSaved() {
        Logger.debug("isLatestVersionSaved for " + getClass().getSimpleName() + ": " + lastSavedHash + ", " + objects.hashCode() + " -- " + objects.toString());
        return lastSavedHash == objects.hashCode();
    }

    public boolean isInitialized() {
        return objects != null;
    }

    public abstract boolean isValid();
    public abstract void close() throws Exception;
}
