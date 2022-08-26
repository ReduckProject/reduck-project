package net.reduck.cipher.internal;

/**
 * A padder that adds PKCS7/PKCS5 padding to a block.
 */
public class PKCS7Padding {
    /**
     * add the pad bytes to the passed in block, returning the
     * number of bytes added.
     */
    public static int addPadding(
            byte[] in,
            int inOff) {
        byte code = (byte) (in.length - inOff);

        while (inOff < in.length) {
            in[inOff] = code;
            inOff++;
        }

        return code;
    }

    /**
     * return the number of pad bytes present in the block.
     */
    public static int padCount(byte[] in) {
        int count = in[in.length - 1] & 0xff;
        byte countAsbyte = (byte) count;

        // constant time version
        boolean failed = (count > in.length | count == 0);

        for (int i = 0; i < in.length; i++) {
            failed |= (in.length - i <= count) & (in[i] != countAsbyte);
        }

        if (failed) {
            throw new RuntimeException("pad block corrupted");
        }

        return count;
    }
}
