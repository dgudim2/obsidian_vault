package org.kloud.model.user;

import org.jetbrains.annotations.NotNull;
import org.kloud.common.Field;
import org.kloud.common.HashedString;
import org.kloud.model.BaseModel;
import org.kloud.utils.Utils;
import org.kloud.utils.card.CardValidationResult;
import org.kloud.utils.card.RegexCardValidator;

import java.util.ArrayList;
import java.util.List;

public abstract class User extends BaseModel {

    public static long ADMIN_ID = 1000;

    public static final List<Class<? extends User>> USERS = List.of(Manager.class, Customer.class);

    public final Field<String> name = new Field<>("Name", true, String.class, v -> Utils.testLength(v, 1, 100));
    public final Field<String> surname = new Field<>("Surname", true, String.class, v -> Utils.testLength(v, 1, 100));
    public final Field<String> cardNumber = new Field<>("Card number", false, String.class, v -> {
        if(v == null || v.isEmpty()) {
            return "";
        }
        CardValidationResult res = RegexCardValidator.isValid(v);
        return res.isValid() ? "" : res.getError();
    });

    public final Field<String> login = new Field<>("Login", true, String.class, v -> Utils.testLength(v, 1, 100));
    public final Field<HashedString> pass = new Field<>("Password", true, HashedString.class, v -> Utils.testLength(v.getRaw(), 8, 100));

    @SuppressWarnings("DuplicatedCode")
    @Override
    public @NotNull List<Field<?>> getFields() {
        List<Field<?>> fields = new ArrayList<>(5);
        fields.add(login);
        fields.add(pass);
        fields.add(name);
        fields.add(surname);
        fields.add(cardNumber);
        return fields;
    }
}
