package org.kloud.common.datatypes;

import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.kloud.utils.Logger;

import java.security.SecureRandom;

import static org.kloud.utils.Utils.bytesToHexStr;
import static org.kloud.utils.Utils.hashPass;

/**
 * Custom datatype that represents a hashed string with salt (typically a password)
 */
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
    public void deserializeFromString(@Nullable String data) {
        if (data == null) {
            set("");
            Logger.warn("Data is null for hashed string: " + this);
            return;
        }
        var parts = data.split("--");
        hashValue = parts[0];
        hashSalt = parts[1];
    }

    @Override
    public String toString() {
        return "**hashed**";
    }
}
