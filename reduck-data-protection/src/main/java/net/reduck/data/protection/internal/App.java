package net.reduck.data.protection.internal;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Gin
 * @since 2023/5/22 13:19
 */
public class App {

    public static void main(String[] args) throws Exception {
        KeyPair keypair = getKeyPair(1024);

        System.out.println(Base64.getEncoder().encodeToString(keypair.getPrivate().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded()));

        String data = Base64.getEncoder().encodeToString(RsaUtils.encrypt(
                "ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234ginsco@1234"

                        .getBytes(), keypair.getPublic()));
        System.out.println(data.length());
    }

    public static KeyPair getKeyPair(int keyLength) throws Exception {
        //BC即BouncyCastle加密包，EC为ECC算法
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }
}
