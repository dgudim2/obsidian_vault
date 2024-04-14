package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.BaseModel;
import org.kloud.utils.Logger;

import java.util.*;

/**
 * Base class for interfacing with the stored data
 *
 * @param <T> Type of objects stored
 */
public abstract class BasicDAO<T extends BaseModel> {
    protected List<T> objects;
    protected HashMap<Long, T> idLookup;

    @NotNull
    protected List<T> readObjects() {
        List<T> r_objects = readObjectsInternal();
        idLookup = new HashMap<>();
        for (var object : r_objects) {
            idLookup.put(object.id, object);
        }
        return r_objects;
    }

    @NotNull
    protected abstract List<T> readObjectsInternal();

    protected abstract boolean writeObjectsInternal();

    protected void ensureObjects() {
        if (objects == null) {
            objects = readObjects();
        }
    }

    @NotNull
    public List<T> getObjects() {
        ensureObjects();
        return Collections.unmodifiableList(objects);
    }

    @Nullable
    public T getById(@Nullable Long id) {
        ensureObjects();
        return idLookup.get(id);
    }

    @NotNull
    public List<T> getByIds(@NotNull List<Long> ids) {
        List<T> lookedUp = new ArrayList<>();
        for (var lid : ids) {
            var obj = getById(lid);
            if (obj == null) {
                Logger.warn("Lookup by id: " + lid + " failed in " + this);
                continue;
            }
            lookedUp.add(obj);
        }
        return lookedUp;
    }

    public boolean addOrUpdateObject(@NotNull T object) {
        ensureObjects();
        var existingObject = objects.stream().filter(o -> o.id == object.id).findFirst();
        if (existingObject.isPresent()) {
            if (Objects.equals(existingObject.get(), object)) {
                Logger.warn("Unnecessary call to addOrUpdateObject (the same object already exists)");
                // Objects are also equal by content
                object.markLatestVersionSaved();
                var res = writeObjectsInternal();
                if (res) {
                    object.markLatestVersionSaved();
                }
                return res;
            }
            objects.remove(existingObject.get());
        }
        idLookup.put(object.id, object);
        objects.add(object);
        var res = writeObjectsInternal();
        if (res) {
            object.markLatestVersionSaved();
        }
        return res;
    }

    public boolean removeObject(@NotNull T object) {
        ensureObjects();
        var removed = objects.remove(object);
        if (removed) {
            idLookup.remove(object.id);
            return writeObjectsInternal();
        }
        return false;
    }

    public boolean hasUnsavedChanges() {
        ensureObjects();
        for (var obj : objects) {
            if (obj.hasChanges()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInitialized() {
        return objects != null;
    }

    public abstract boolean isValid();

    public abstract void close();
}
