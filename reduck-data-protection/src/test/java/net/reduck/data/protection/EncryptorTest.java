package net.reduck.data.protection;

import net.reduck.data.protection.env.EncryptionWrapperDetector;
import net.reduck.data.protection.env.SimpleEncryptor;
import net.reduck.data.protection.env.encryptor.SimplePBEByteEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.junit.Test;

/**
 * @author Gin
 * @since 2023/5/6 15:31
 */
public class EncryptorTest {
    private final SimpleEncryptor encryptor;
    private final EncryptionWrapperDetector detector;
    public EncryptorTest() {
        SimplePBEByteEncryptor encryptor = new SimplePBEByteEncryptor();
        encryptor.setPassword("reduck-project");
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIterations(64);
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        this.encryptor = new SimpleEncryptor(encryptor);
        this.detector = new EncryptionWrapperDetector("$ENC{", "}");
    }

    @Test
    public void testEncrypt() {

        String message = detector.wrapper(encryptor.encrypt(""));

        System.out.println(message);

        System.out.println(encryptor.decrypt(detector.unWrapper(message)));
    }
}
