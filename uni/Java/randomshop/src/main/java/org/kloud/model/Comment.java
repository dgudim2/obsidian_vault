
import org.jetbrains.annotations.NotNull;
import org.kloud.common.Fields.Field;
import org.kloud.common.Fields.ForeignKeyField;
import org.kloud.common.Fields.ForeignKeyListField;
import org.kloud.common.Fields.RatingField;
import org.kloud.model.user.User;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.Utils;

import java.util.List;

public class Comment extends BaseModel {

    public final Field<String> title = new Field<>("Title", true, String.class, s -> Utils.testLength(s, 1, 50));
    public final Field<String> content = new Field<>("Content", true, String.class, s -> Utils.testLength(s, 1, 250));
    public final RatingField rating = new RatingField("Rating");

    public final ForeignKeyField<User> author = new ForeignKeyField<>("Author", true,
            id -> ConfigurationSingleton.getStorage().getUserStorage().getById(id), List::of, user -> {
    });

    public final ForeignKeyListField<Comment> children = new ForeignKeyListField<>("Children",
            ids -> ConfigurationSingleton.getStorage().getCommentStorage().getByIds(ids), List::of, (comments, comments2) -> {
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
}
