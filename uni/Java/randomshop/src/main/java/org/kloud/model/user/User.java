package org.kloud.model.user;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.kloud.model.enums.UserCapability;
import org.kloud.common.datatypes.HashedString;
import org.kloud.common.fields.Field;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;
import org.kloud.utils.Conf;
import org.kloud.utils.Logger;
import org.kloud.utils.Utils;
import org.kloud.utils.card.CardValidationResult;
import org.kloud.utils.card.RegexCardValidator;

import java.util.*;

import static org.kloud.utils.Utils.hashPass;

@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public abstract class User extends BaseModel {

    @EqualsAndHashCode.Exclude
    public static final long ADMIN_ID = 1000;

    @EqualsAndHashCode.Exclude
    public static final List<Class<? extends User>> USERS = List.of(Manager.class, Customer.class);

    public final Field<String> name = new Field<>("Name", true, String.class, v -> Utils.testLength(v, 1, 100));
    public final Field<String> surname = new Field<>("Surname", true, String.class, v -> Utils.testLength(v, 1, 100));
    public final Field<String> cardNumber = new Field<>("Card number", false, String.class, v -> {
        if (v == null || v.isEmpty()) {
            return "";
        }
        CardValidationResult res = RegexCardValidator.isValid(v);
        return res.isValid() ? "" : res.getError();
    });

    public final Field<String> login = new Field<>("Login", true, String.class, newLogin -> {
        var lenWarning = Utils.testLength(newLogin, 1, 100);
        if (!lenWarning.isEmpty()) {
            return lenWarning;
        }
        // NOTE: Using stream apis of small arrays is meh, investigate performance
        if (Conf.getStorage()
                .getUserStorage().getObjects()
                .stream()
                .anyMatch(user -> Objects.equals(user.login.get(), newLogin) && user.id != id)) {
            // NOTE: This is inefficient, this will traverse and check every user in the worst case on each character typed
            return "A user with the same login already exists";
        }
        return "";
    });
    public final Field<HashedString> pass = new Field<>("Password", true, HashedString.class, v -> {
        // Raw is set when we start typing in the field, .get() is set when the field is loaded from backend or set successfully
        var rawValue = v.getRaw();
        if (rawValue != null) {
            return Utils.testLength(rawValue, 8, 100);
        }
        return "";
    });

    public final ForeignKeyListField<Comment> comments = new ForeignKeyListField<>("Comments", false, false, () -> true,
            ids -> Conf.getStorage().getCommentStorage().getByIds(ids), List::of, (comments1, comments2) -> {
    });

    User() {
        super();
    }

    public User(long id) {
        super(id);
    }

    public Set<UserCapability> getUserCaps() {
        var caps = new HashSet<UserCapability>();
        caps.add(UserCapability.CHANGE_SELF_PASSWORD);
        caps.add(UserCapability.RW_SELF_COMMENTS);
        caps.add(UserCapability.RW_SELF_ORDERS);
        caps.add(UserCapability.READ_OTHER_COMMENTS);
        return caps;
    }

    public boolean checkPassword(@NotNull String inputPass) {
        var actualPass = pass.get().get();
        if(actualPass == null) {
            // WHAT?: When will this occur? IDK, but still handle, should we throw an exception here?
            Logger.error("actualPass is null, failed checking password");
            return false;
        }
        return hashPass(inputPass, actualPass.getValue()).equals(actualPass.getKey());
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public @NotNull List<Field<?>> getFields() {
        return new ArrayList<>(List.of(login, pass, name, surname, cardNumber, comments));
    }
}
