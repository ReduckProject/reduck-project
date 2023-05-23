package net.reduck.data.protection;

import net.reduck.data.protection.internal.Sm4Utils;
import org.junit.jupiter.api.Test;

/**
 * @author Gin
 * @since 2023/5/22 15:46
 */
public class Sm4UtilsTest {

    @Test
    public void testNoPadding() {
        String key = "1234567812345678";

        byte[] data = Sm4Utils.encryptNoPadding("ginsco@123456789".getBytes(), key.getBytes());
        byte[] origin = Sm4Utils.decryptNoPadding(data, key.getBytes());

        System.out.println(new String(data));
        System.out.println(new String(origin));
    }
}
