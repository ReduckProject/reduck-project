package net.reduck.asm;

/**
 * @author Reduck
 * @since 2023/1/31 14:34
 */
public class ClassloaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(Logger.class.getClassLoader().getClass().getName());
//        Logger.class.getClassLoader().loadClass();

        Thread.currentThread().getContextClassLoader();

    }
}
