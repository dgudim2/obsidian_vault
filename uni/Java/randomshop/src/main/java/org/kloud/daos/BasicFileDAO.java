package org.kloud.daos;

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
    protected List<T> readObjectsInternal() {
        return readObject(getFilePath(), new ArrayList<>());
    }

    protected boolean writeObjectsInternal() {
        return writeObject(objects, getFilePath());
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
