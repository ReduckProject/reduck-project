package net.reduck.data.protection.env;

import net.reduck.data.protection.internal._AesUtils;

import java.util.Base64;

/**
 * @author Gin
 * @since 2023/5/23 19:35
 */
public class AesEncryptor implements Encryptor {

    private final byte[] secretKey;
    private final byte[] iv = new byte[16];

    public AesEncryptor(byte[] secretKey) {
        this.secretKey = secretKey;
        System.arraycopy(secretKey, 0, iv, 0, 16);
    }

    @Override
    public String encrypt(String message) {
        return Base64.getEncoder().encodeToString(_AesUtils.encrypt(secretKey, iv, message.getBytes()));
    }

    @Override
    public String decrypt(String message) {
        return new String(_AesUtils.decrypt(secretKey, iv, Base64.getDecoder().decode(message)));
    }
}
