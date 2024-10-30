package net.reduck.data.protection.internal;

import lombok.SneakyThrows;
import sun.security.pkcs.PKCS8Key;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Gin
 * @since 2021/6/5 17:23
 */
public class RsaUtils {

    public static byte[] encrypt(byte[] data, PublicKey key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(byte[] data, byte[] key) {
        return encrypt(data, getPublicKey(key));
    }


    public static byte[] decrypt(byte[] data, PrivateKey key) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            return decrypt(data, PKCS8Key.parseKey(new DerValue(key)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair getKeyPair(int keyLength) throws Exception {
        //BC即BouncyCastle加密包，EC为ECC算法
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    @SneakyThrows
    public static PrivateKey getPrivateKey(byte[] bytes) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    @SneakyThrows
    public static PublicKey getPublicKey(byte[] bytes) {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = getKeyPair(2048);
        System.out.println(keyPair.getPrivate());
    }
}
