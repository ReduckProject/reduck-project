package net.reduck.asm;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author Reduck
 * @since 2022/8/29 17:17
 */
public class AsmTest {
    public static void main(String[] args) throws Exception {
        //类加载
        byte[] clazzByte = loadClass();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Method method = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        method.setAccessible(true);
        Class clazz = (Class) method.invoke(classLoader, clazzByte, 0, clazzByte.length);

        //类链接
        method = Class.forName("java.lang.ClassLoader")
                .getDeclaredMethod("resolveClass", Class.class);
        method.setAccessible(true);
        method.invoke(classLoader,clazz);

        Method testM = clazz.getDeclaredMethod("test");
        testM.setAccessible(true);
        testM.invoke(clazz.newInstance());
    }


    public static byte[] loadClass() throws Exception {
        String path = "C:\\Users\\HelloC.class";
        FileInputStream in = new FileInputStream(path);
        long fileSize = new File(path).length();
        byte[] clazzSize = new byte[(int) fileSize];
        in.read(clazzSize);

        int reduck = 33;
        System.out.println(reduck);
        return clazzSize;
    }
}
