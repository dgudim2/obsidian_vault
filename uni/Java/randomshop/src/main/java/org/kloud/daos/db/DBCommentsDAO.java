package org.kloud.daos.db;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;

import java.util.List;

public class DBCommentsDAO extends BasicDBDAO<Comment> {
    @Override
    protected @NotNull String getTableName() {
        return "comments";
    }

    @Override
    protected @NotNull List<? extends Comment> getStoredClasses() {
        return List.of(new Comment(BaseModel.DUMMY_ID));
    }
}
