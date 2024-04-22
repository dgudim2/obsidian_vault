package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.BaseModel;
import org.kloud.utils.Logger;

import java.util.*;
import java.util.function.Predicate;

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
        // TODO: Retry on fail maybe (don't return empty arrays from readObjectsInternal on fail)
        List<T> r_objects = readObjectsInternal();
        idLookup = new HashMap<>();
        for (var object : r_objects) {
            idLookup.put(object.id, object);
        }
        return r_objects;
    }

    //WHAT?: Hacky way to make sure that the list is mutable
    @NotNull
    protected abstract ArrayList<T> readObjectsInternal();

    protected abstract boolean writeObjectsInternal();

    private void ensureObjects() {
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

    public List<T> getWithFilter(Predicate<T> predicate) {
        return getObjects().stream().filter(predicate).toList();
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

    // TODO: Return a status instead (Added/Updated/Failed)
    public boolean addOrUpdateObject(@NotNull T object) {
        ensureObjects();
        var existingObject = objects.stream().filter(o -> o.id == object.id).findFirst();
        if (existingObject.isPresent()) {
            if (Objects.equals(existingObject.get(), object)) {
                Logger.weakWarn("Unnecessary call to addOrUpdateObject (the same object already exists)");
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
        } else {
            Logger.warn("Could not remove " + object);
        }
        return false;
    }

    public boolean removeById(@Nullable Long id) {
        ensureObjects();
        var obj = getById(id);
        if(obj == null) {
            return false;
        }
        return removeObject(obj);
    }

    public boolean removeByIds(@NotNull List<Long> ids) {
        ensureObjects();
        var objs = getByIds(ids);
        boolean success = true;
        for(var obj: objs) {
            var succ = removeObject(obj);
            success = succ && success;
        }
        return success;
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
