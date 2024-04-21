package org.kloud.model;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyField;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.common.fields.RatingField;
import org.kloud.model.user.User;
import org.kloud.utils.Conf;
import org.kloud.utils.Utils;

import java.util.List;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Comment extends BaseModel {

    public final Field<String> title = new Field<>("Title", true, String.class, s -> Utils.testLength(s, 1, 50));
    public final Field<String> content = new Field<>("Content", true, String.class, s -> Utils.testLength(s, 1, 250));
    public final RatingField rating = new RatingField("Rating");

    public final ForeignKeyField<User> author = new ForeignKeyField<>("Author", true, () -> true,
            id -> Conf.getStorage().getUserStorage().getById(id), List::of, (user, newUser) -> {
    });

    public final ForeignKeyListField<Comment> children = new ForeignKeyListField<>("Children", false, false, () -> true,
            ids -> Conf.getStorage().getCommentStorage().getByIds(ids), List::of, (comments, comments2) -> {
    });

    public Comment() {
        super();
    }

    public Comment(long id) {
        super(id);
    }

    @Override
    public @NotNull List<Field<?>> getFields() {
        return List.of(title, content, rating, author, children);
    }

    @Override
    public String isSafeToDelete() {
        return "";
    }

    @Override
    protected @NotNull String toStringInternal() {
        return "";
    }

    @Override
    public String toString() {
        var authorVal = author.getLinkedValue();
        return title + " (" + rating + "☆) by " + (authorVal == null ? "unknown" : "'" + authorVal.name.get() + " " + authorVal.surname.get() + "'");
    }
}
