package org.kloud.daos.file;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.Comment;

public class FileCommentsDAO extends BasicFileDAO<Comment> {
    @Override
    protected @NotNull String getFileName() {
        return "comments";
    }
}
