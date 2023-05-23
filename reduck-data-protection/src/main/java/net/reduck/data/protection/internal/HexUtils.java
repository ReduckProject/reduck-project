package net.reduck.data.protection.internal;

/**
 * User Canno
 * Date 2018/1/13
 * Time 21:39
 */
public class HexUtils {
    private static final String[] HEX_DIGITS = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
    };

    /**
     * hex string  to byte[]
     *
     * @param hexStr Hex String
     *
     * @return byte[]
     */
    public static byte[] hex2byte(String hexStr) {
        if (hexStr == null) {
            return null;
        }

        int len = hexStr.length();
        if ((len & 1) == 1) {
            return null;
        }

        byte[] b = new byte[len / 2];
        for (int i = 0; i < len / 2; i++) {
            b[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
        }

        return b;
    }

    /**
     * byte[] to hex string
     *
     * @param b byte
     *
     * @return Hex String
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        for (byte aB : b) {
            hs.append(byte2HexString(aB));
        }

        return hs.toString();
    }

    /**
     * a byte to hex string
     *
     * @param b byte
     *
     * @return Hex String
     */
    protected static String byte2HexString(byte b) {
        int n = b;

        if (n < 0) {
            n += 256;
        }

        int high = n >>> 4;
        int low = n & 15;

        return HEX_DIGITS[high] + HEX_DIGITS[low];
    }
}
