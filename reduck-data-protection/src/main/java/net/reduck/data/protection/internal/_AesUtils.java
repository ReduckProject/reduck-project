package net.reduck.data.protection.internal;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @author Gin
 * @since 2023/5/23 17:03
 */
public class _AesUtils {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    @SneakyThrows
    public static byte[] encrypt(byte[] key, byte[] iv, byte[] data) {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    @SneakyThrows
    public static byte[] decrypt(byte[] key, byte[] iv, byte[] data) {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    @SneakyThrows
    public static void main(String[] args) {
        byte[] data = MessageDigest.getInstance("SHA-512").digest("reduck-project".getBytes());
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        System.arraycopy(data, 0, key, 0, 32);
        System.arraycopy(data, 32, iv, 0, 16);

        StringBuilder sb = new StringBuilder();
        for(int i =0; i < data.length; i++) {
            sb.append(",").append(data[i]);
            if(i % 8 == 0) {
                sb.append("\n");
            }
        }

        FormatPrinter.printHex(data, 8);
        System.out.println(Integer.toHexString(3));
        System.out.println(sb.toString());
        System.out.println(HexUtils.byte2hex(encrypt(key, iv, "ginsco".getBytes())));
    }
}
