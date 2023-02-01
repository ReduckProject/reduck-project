package net.reduck.chat.server.io;

import net.reduck.chat.server.utils.Ciphers;

/**
 * @author Reduck
 * @since 2023/1/9 11:24
 */
public class MessageCipher {

    private final byte[] key;

    public MessageCipher(byte[] key) {
        this.key = key;
    }

    public byte[] encrypt(byte[] data) {
        return Ciphers.encrypt(data, key);
    }

    public byte[] decrypt(byte[] data) {
        return Ciphers.decrypt(data, key);
    }

    public static strictfp void main(String[] args) {
        System.out.println(0.1f + 0.2f - 0.2f);
    }
}
