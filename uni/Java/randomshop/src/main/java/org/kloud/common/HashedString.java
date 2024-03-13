package org.kloud.common;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.security.SecureRandom;

import static org.kloud.utils.Utils.bytesToHexStr;
import static org.kloud.utils.Utils.hashPass;

public class HashedString implements Serializable {

    private String hashValue;
    private String hashSalt;

    private transient String rawValue;

    public HashedString() {

    }

    public HashedString(@NotNull String newValue) {
        set(newValue);
    }

    @NotNull
    public String getRaw() {
        return rawValue == null ? "" : rawValue;
    }

    public void set(@NotNull String newValue) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        rawValue = newValue;
        hashSalt = bytesToHexStr(salt);
        hashValue = hashPass(newValue, hashSalt);
    }

    public Pair<String, String> get() {
        if (hashValue.isEmpty()) {
            return null;
        }
        return new Pair<>(hashValue, hashSalt);
    }

}
