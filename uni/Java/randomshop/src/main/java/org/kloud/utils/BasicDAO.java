package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BasicDAO<T extends BaseModel> {
    protected List<T> objects;

    protected int lastSavedHash = -1;

    @NotNull
    protected abstract List<T> readObjects();

    protected void postRead(@NotNull ArrayList<T> r_objects) {
        for (var product : r_objects) {
            product.postRead();
        }
    }

    @NotNull
    public List<T> getObjects() {
        if(objects == null) {
            objects = readObjects();
        }
        return Collections.unmodifiableList(objects);
    }

    public abstract boolean addObject(@NotNull T product);

    public abstract boolean removeObject(@NotNull T product);

    public boolean isLatestVersionSaved() {
        System.out.println("isLatestVersionSaved for " + getClass().getSimpleName() + ": " + lastSavedHash + ", " + objects.hashCode());
        System.out.println(objects);
        return lastSavedHash == objects.hashCode();
    }
}
