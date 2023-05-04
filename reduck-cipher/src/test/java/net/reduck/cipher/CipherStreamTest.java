package net.reduck.cipher;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Reduck
 * @since 2022/11/9 17:52
 */
public class CipherStreamTest {

    @Test
    public void testStream() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("PBEWithHmacSHA1AndAES_256");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("123".getBytes(), "PBEWithHmacSHA1AndAES_256"));
//        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES"));

        System.out.println(HexBin.encode(cipher.getIV()));
        InputStream in = new CipherInputStream(new ByteArrayInputStream("12345678".getBytes()), cipher);

        cipher.getParameters().getEncoded();

        String data = HexBin.encode(FileCopyUtils.copyToByteArray(in));

        Cipher decCipher = Cipher.getInstance("PBEWithHmacSHA1AndAES_256");
        decCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("123".getBytes(), "PBEWithHmacSHA1AndAES_256"));
//        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES"));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = new CipherOutputStream(bos, decCipher);
        os.write(HexBin.decode(data));
        os.flush();
        os.close();
        System.out.println(new String(bos.toByteArray()));
    }

    @Test
    public void testPbe() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES"), new IvParameterSpec(new byte[16]));
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES"));

        System.out.println(HexBin.encode(cipher.getIV()));
        InputStream in = new CipherInputStream(new ByteArrayInputStream("12345678".getBytes()), cipher);

        String data = HexBin.encode(FileCopyUtils.copyToByteArray(in));

        System.out.println(data);
    }
}
