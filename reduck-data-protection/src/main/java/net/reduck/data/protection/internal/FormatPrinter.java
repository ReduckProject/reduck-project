package net.reduck.data.protection.internal;

/**
 * @author Gin
 * @since 2023/5/23 17:20
 */
public class FormatPrinter {

    public static void main(String[] args) {
        int startRange = -128;
        int endRange = 127;

        for (int i = startRange; i <= endRange; i++) {
            System.out.printf("(byte)0x%02X, ", i & 0xFF);

            if ((i - startRange + 1) % 16 == 0) {
                System.out.println();
            }
        }
    }

    public static void printHex(byte[] data, int line) {
        for (int i = 0; i < data.length; i++) {
            System.out.printf("(byte)0x%02X, ", data[i] & 0xFF);
            if((i + 1) % line == 0) {
                System.out.println();
            }
        }
    }
}
