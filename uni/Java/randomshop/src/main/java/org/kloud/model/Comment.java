package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Fields.Field;
import org.kloud.common.Fields.RatingField;
import org.kloud.utils.Utils;

import java.util.List;

public class Comment extends BaseModel {

    public final Field<String> title = new Field<>("Title", true, String.class, s -> Utils.testLength(s, 1, 50));
    public final Field<String> content = new Field<>("Content", true, String.class, s -> Utils.testLength(s, 1, 250));
    public final RatingField rating = new RatingField("Rating");

    @Override
    public @NotNull List<Field<?>> getFields() {
        return List.of();
    }

    @Override
    public String isSafeToDelete() {
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        return "";
    }
}
