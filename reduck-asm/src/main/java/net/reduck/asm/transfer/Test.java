package net.reduck.asm.transfer;

import net.reduck.asm.DynamicClassLoader;

import java.lang.reflect.Method;

/**
 * @author Gin
 * @since 2023/2/3 15:07
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String name = "net.reduck.asm.transfer.Transfer";
        DynamicClassLoader classLoader = new DynamicClassLoader(Thread.currentThread().getContextClassLoader());

        byte[] data = TransferDump.dump();
        classLoader.loadClass(name, data);

//        Thread.currentThread().getContextClassLoader().loadClass("net/reduck/asm/transfer/Transfer");
        Method method = classLoader.loadClass(name).getMethods()[0];

        Apple apple = new Apple();
        apple.setHeight(10000);
        apple.setWidth(333333L);
        apple.setHealth(true);
        apple.setName("Apple gooooooooooood nicccccccccce");
        apple.setPassword("banana banananannanananananana");
        int count = 10000000;

        long begin, end;

        System.gc();

        begin = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
//            tranfer(apple);
            method.invoke(null, apple);
        }
        end = System.currentTimeMillis();
        System.out.println("Case 2 cost " + (end - begin) + " ms");

        System.gc();

        begin = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            MapperUtils.map(apple, AppleTO.class);
        }

        end = System.currentTimeMillis();

        System.out.println("Case 1 cost " + (end - begin) + " ms");
    }
}
