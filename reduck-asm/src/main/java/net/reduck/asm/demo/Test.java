package net.reduck.asm.demo;

import net.reduck.asm.util.Magician;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Reduck
 * @since 2022/9/5 20:01
 */
public class Test {
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000000; i++) {
                num.getAndAdd(1);
//                Magician.preventLongTimeGc(i % 1000 == 0);
            }
            System.out.println(Thread.currentThread().getName() + "执行结束!");
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
//        Thread.sleep(1000);
        System.out.println("num = " + num);
    }

    private static void preventGc(int i, int cycle) {
        if (i % cycle == 0) {
            Thread.yield();
        }
    }

}
