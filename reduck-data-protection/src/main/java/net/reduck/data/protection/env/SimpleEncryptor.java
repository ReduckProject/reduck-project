package net.reduck.data.protection.env;

import net.reduck.data.protection.env.encryptor.SimplePBEByteEncryptor;

import java.util.Base64;

/**
 * @author Gin
 * @since 2023/5/6 14:58
 */
public class SimpleEncryptor implements Encryptor {

    private final SimplePBEByteEncryptor simplePBEByteEncryptor;

    public SimpleEncryptor(SimplePBEByteEncryptor simplePBEByteEncryptor) {
        this.simplePBEByteEncryptor = simplePBEByteEncryptor;
    }

    @Override
    public String encrypt(String message) {
        return Base64.getEncoder().encodeToString(simplePBEByteEncryptor.encrypt(message.getBytes()));
    }

    @Override
    public String decrypt(String message) {

        return new String(simplePBEByteEncryptor.decrypt(Base64.getDecoder().decode(message)));
    }
}
