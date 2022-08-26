package net.reduck.cipher.util;

import net.reduck.cipher.internal.KeyParameter;
import net.reduck.cipher.internal.PKCS7Padding;
import net.reduck.cipher.internal.SM4Engine;

/**
 * @author Reduck
 * @since 2021/7/9 14:13
 */
public class Sm4Utils {

    public static byte[] encrypt(byte[] data, byte[] key) {
        SM4Engine engine = new SM4Engine();
        engine.init(true, new KeyParameter(key));
        int blockSize = engine.getBlockSize();
        byte[] paddingData = new byte[(data.length / blockSize + 1) * blockSize];
        System.arraycopy(data, 0, paddingData, 0, data.length);
        PKCS7Padding.addPadding(paddingData, data.length);

        byte[] out = new byte[paddingData.length];

        for (int i = 0; i < paddingData.length / blockSize; i++) {
            engine.processBlock(paddingData, blockSize * i, out, blockSize * i);
        }

        return out;
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        SM4Engine engine = new SM4Engine();
        engine.init(false, new KeyParameter(key));
        int blockSize = engine.getBlockSize();

        byte[] out = new byte[data.length];

        for (int i = 0; i < data.length / blockSize; i++) {
            engine.processBlock(data, blockSize * i, out, blockSize * i);
        }

        int paddingCount = PKCS7Padding.padCount(out);

        if (paddingCount == 0) {
            return out;
        }

        byte[] originalData = new byte[data.length - paddingCount];
        System.arraycopy(out, 0, originalData, 0, originalData.length);
        return originalData;
    }
}
