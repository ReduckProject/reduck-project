package net.reduck.jpa.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Reduck
 * @since 2022/9/9 18:03
 */
public class LockTest {

    public static class MyThread extends Thread {

        @Override
        public void run() {

            System.out.println("hhhhhhhhhhhhh");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MyThread myThread = new MyThread();
        executorService.execute(myThread);

        myThread.start();
        System.out.println(myThread.getState());


        Thread.sleep(1000);

        System.out.println(myThread.getState());

        Thread.sleep(2000);

        System.out.println(myThread.getState());

        myThread.start();
        System.out.println(myThread.getState());

        Thread.sleep(2000);

    }
}
