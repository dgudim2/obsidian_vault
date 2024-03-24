package org.kloud.utils;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;

import java.util.List;

public class BasicDBDAO<T extends BaseModel> extends BasicDAO<T> {


    @Override
    protected @NotNull List<T> readObjects() {
        return null;
    }

    @Override
    public boolean addOrUpdateObject(@NotNull T product) {
        return false;
    }

    @Override
    public boolean removeObject(@NotNull T product) {
        return false;
    }
}
