package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.kloud.utils.Utils.readObject;
import static org.kloud.utils.Utils.writeObject;

public abstract class BasicFileDAO<T extends BaseModel> extends BasicDAO<T> {

    @NotNull
    protected abstract String getFilePath();
    @Override
    @NotNull
    protected List<T> readObjects() {
        ArrayList<T> r_objects = readObject(getFilePath(), new ArrayList<>());
        postRead(r_objects);
        lastSavedHash = r_objects.hashCode();
        return r_objects;
    }

    @Override
    public boolean addOrUpdateObject(@NotNull T object) {
        var existingObject = objects.stream().filter(o -> o.id == object.id).findFirst();
        if (existingObject.isPresent()) {
            if(Objects.equals(existingObject.get(), object)) {
                Logger.warn("Unnecessary call to addOrUpdateObject (the same object already exists)");
                return true;
            }
            objects.remove(existingObject.get());
        }
        objects.add(object);
        return writeObjects();
    }

    @Override
    public boolean removeObject(@NotNull T object) {
        var removed = objects.remove(object);
        if (removed) {
            return writeObjects();
        }
        return false;
    }

    private boolean writeObjects() {
        lastSavedHash = objects.hashCode();
        return writeObject(objects, getFilePath());
    }
}
