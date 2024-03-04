package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

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
    public boolean addObject(@NotNull T object) {
        if (!objects.contains(object)) {
            objects.add(object);
        }
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
