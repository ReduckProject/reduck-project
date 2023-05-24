package net.reduck.data.protection.env;

import lombok.SneakyThrows;
import net.reduck.data.protection.internal.RsaUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;

/**
 * @author Gin
 * @since 2023/5/23 16:09
 */
public class PrivateKeyFinder {
    private static final String PRIVATE_KEY_RESOURCE_LOCATION = "META-INF/configuration.crypto.private-key";
    private static final String PUBLIC_KEY_RESOURCE_LOCATION = "META-INF/configuration.crypto.public-key";
    private final byte[] keyInfo = new byte[]{
            (byte) 0xD0, (byte) 0x20, (byte) 0xDA, (byte) 0x92, (byte) 0xC8, (byte) 0x0B, (byte) 0x6D, (byte) 0x57,
            (byte) 0x48, (byte) 0x7B, (byte) 0x15, (byte) 0x3A, (byte) 0x44, (byte) 0xA0, (byte) 0x98, (byte) 0xC2,
            (byte) 0xF1, (byte) 0x6F, (byte) 0xB6, (byte) 0x09, (byte) 0x2F, (byte) 0x6D, (byte) 0x69, (byte) 0xFB,
            (byte) 0x2D, (byte) 0x02, (byte) 0x00, (byte) 0xCB, (byte) 0xBE, (byte) 0x48, (byte) 0xDD, (byte) 0xD5,
            (byte) 0x90, (byte) 0xC2, (byte) 0x95, (byte) 0x98, (byte) 0x60, (byte) 0x59, (byte) 0x24, (byte) 0xE2,
            (byte) 0xB7, (byte) 0x84, (byte) 0x12, (byte) 0x5D, (byte) 0xB9, (byte) 0xC1, (byte) 0x19, (byte) 0xFF,
            (byte) 0x4F, (byte) 0x01, (byte) 0xB9, (byte) 0xC5, (byte) 0xD8, (byte) 0xD2, (byte) 0x99, (byte) 0xEE,
            (byte) 0xAA, (byte) 0x0D, (byte) 0x59, (byte) 0xF8, (byte) 0x37, (byte) 0x49, (byte) 0x91, (byte) 0xAB
    };

    static byte[] getSecretKey(String encKey) {
        byte[] key = loadPrivateKey();
        return RsaUtils.decrypt(Base64.getDecoder().decode(encKey), new PrivateKeyFinder().decrypt(Base64.getDecoder().decode(key)));
    }

    static String generateSecretKey() {
        return Base64.getEncoder().encodeToString(RsaUtils.encrypt(new SecureRandom().generateSeed(16), Base64.getDecoder().decode(loadPublicKey())));
    }

    static String generateSecretKeyWith256() {
        return Base64.getEncoder().encodeToString(RsaUtils.encrypt(new SecureRandom().generateSeed(32), Base64.getDecoder().decode(loadPublicKey())));
    }

    @SneakyThrows
    static byte[] loadPrivateKey() {
        return loadResource(PRIVATE_KEY_RESOURCE_LOCATION);
    }

    @SneakyThrows
    static byte[] loadPublicKey() {
        return loadResource(PUBLIC_KEY_RESOURCE_LOCATION);
    }

    @SneakyThrows
    private static byte[] loadResource(String location) {
        // just lookup from current jar  path
        ClassLoader classLoader = new URLClassLoader(new URL[]{PrivateKeyFinder.class.getProtectionDomain().getCodeSource().getLocation()}, null);
//        classLoader = PrivateKeyFinder.class.getClassLoader();
        Enumeration<URL> enumeration = classLoader.getResources(location);

        // should only find one
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            UrlResource resource = new UrlResource(url);
            return FileCopyUtils.copyToByteArray(resource.getInputStream());
        }

        return null;
    }

    private final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    private final String KEY_TYPE = "AES";

    @SneakyThrows
    public byte[] encrypt(byte[] data) {
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        System.arraycopy(keyInfo, 0, key, 0, 32);
        System.arraycopy(keyInfo, 32, iv, 0, 16);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_TYPE), new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    @SneakyThrows
    public byte[] decrypt(byte[] data) {
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        System.arraycopy(keyInfo, 0, key, 0, 32);
        System.arraycopy(keyInfo, 32, iv, 0, 16);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, KEY_TYPE), new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    public static void main(String[] args) {
        byte[] key = loadPrivateKey();

        System.out.println(key.length);
        byte[] enc = Base64.getEncoder().encode(new PrivateKeyFinder().encrypt(Base64.getDecoder().decode(key)));

        byte[] dec = Base64.getEncoder().encode(new PrivateKeyFinder().decrypt(Base64.getDecoder().decode(enc)));

        assert !Arrays.equals(key, enc);
        assert !Arrays.equals(key, dec);

        System.out.println(new String(key));
        System.out.println(new String(enc));
        System.out.println(new String(dec));

        byte[] secretKey = Base64.getEncoder().encode(
                RsaUtils.encrypt("reduck---project".getBytes(), Base64.getDecoder().decode(loadPublicKey())
                ));

        System.out.println(new String(secretKey));

        String origin = new String(RsaUtils.decrypt(Base64.getDecoder().decode(secretKey), Base64.getDecoder().decode(loadPrivateKey())));
        System.out.println(origin);
    }
}
