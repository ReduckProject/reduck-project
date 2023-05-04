package net.reduck.core.util;

import java.io.FileOutputStream;

/**
 * @author Gin
 * @since 2023/2/6 17:21
 */
public class IoUtils {


    public static void save(String path, byte[] data) {
        try {
            FileOutputStream os = new FileOutputStream(path);
            os.write(data);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
