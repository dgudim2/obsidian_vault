package org.kloud.common;

import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;

import static org.kloud.utils.Utils.bytesToHexStr;
import static org.kloud.utils.Utils.hashPass;

public class HashedString extends CustomDatatype {

    private String hashValue;
    private String hashSalt;

    private transient String rawValue;

    public HashedString() {

    }

    public HashedString(@Nullable String newValue) {
        set(newValue);
    }

    @Nullable
    public String getRaw() {
        return rawValue;
    }

    public void set(@Nullable String newValue) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        rawValue = newValue;
        if (newValue != null) {
            hashSalt = bytesToHexStr(salt);
            hashValue = hashPass(newValue, hashSalt);
        }
    }

    @Nullable
    public Pair<String, String> get() {
        if (hashValue.isEmpty()) {
            return null;
        }
        return new Pair<>(hashValue, hashSalt);
    }

    @Override
    public String serializeToString() {
        return hashValue + "--" + hashSalt;
    }

    @Override
    public void deserializeFromString(String data) {
        var parts = data.split("--");
        hashValue = parts[0];
        hashSalt = parts[1];
    }
}
