package net.reduck.jpa.test.controller;

/**
 * @author Gin
 * @since 2023/2/15 15:18
 */
public class DataUtils {

    private static String key = AESTool.getMD5("SJFWKZWG-JJD");
    private static String initialVector = "0000000000000000";

    public static String enc(String data) {
        try {
            return AESTool.encrypt(data, key, initialVector);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String dec(String data) {
        try {
            return AESTool.decrypt(data, key, initialVector);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decByDefault(String data){
        try {
            return AESTool.decrypt("12345678901234567890123456789012", key, initialVector);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

