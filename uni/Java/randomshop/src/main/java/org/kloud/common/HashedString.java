package org.kloud.common;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashedString implements Serializable {

    private String hashValue;
    private String hashSalt;

    private transient String rawValue;

    public HashedString() {

    }

    public HashedString(@NotNull String newValue) {
        set(newValue);
    }

    private String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    private String bytesToStr(byte[] bytes) {
        StringBuilder hexStringBuffer = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    @NotNull
    public String getRaw() {
        return rawValue == null ? "" : rawValue;
    }

    public void set(@NotNull String newValue) {

        rawValue = newValue;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        hashSalt = bytesToStr(salt);

        KeySpec spec = new PBEKeySpec(newValue.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            hashValue = bytesToStr(factory.generateSecret(spec).getEncoded());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<String, String> get() {
        if (hashValue.isEmpty()) {
            return null;
        }
        return new Pair<>(hashValue, hashSalt);
    }

}
