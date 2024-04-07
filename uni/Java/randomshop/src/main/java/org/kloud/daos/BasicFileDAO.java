package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import static org.kloud.utils.Utils.readObject;
import static org.kloud.utils.Utils.writeObject;

/**
 * Implementation of {@link BasicDAO} for interaction with a file, just serializes
 * @param <T> Type of objects stored
 */
public abstract class BasicFileDAO<T extends BaseModel> extends BasicDAO<T> {

    @NotNull
    protected abstract String getFileName();
    @Override
    @NotNull
    protected List<T> readObjectsInternal() {
        return readObject(getFileName(), new ArrayList<>());
    }

    protected boolean writeObjectsInternal() {
        return writeObject(objects, getFileName());
    }

    @Override
    protected @NotNull List<T> readObjects() {
        var objects = super.readObjects();
        for(var obj: objects) {
            obj.postRead();
        }
        return objects;
    }

    @Override
    public boolean isValid() {
        // Always valid, there is no db connection or something
        return true;
    }

    @Override
    public void close() {
        // No need to close anything
    }
}
