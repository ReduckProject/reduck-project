package net.reduck.asm.util;

/**
 * @author Reduck
 * @since 2022/9/5 20:17
 */
public class Magician {

    public static void preventLongTimeGc() {
        Thread.yield();
    }

    public static void preventLongTimeGc(boolean prevent) {
        if (prevent) {
            Thread.yield();
        }
    }
}
