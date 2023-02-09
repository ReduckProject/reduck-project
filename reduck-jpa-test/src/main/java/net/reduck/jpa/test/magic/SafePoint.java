package net.reduck.jpa.test.magic;

import static java.lang.Float.NaN;

/**
 * @author Reduck
 * @since 2022/11/29 15:02
 */
public class SafePoint {

    public static void main(String[] args) {
//        for (int i = 0; i < 1000; i++) {
//            byte[] data;
//            if (i % 2 == 1) {
//                data = new byte[1024 * 1024 * 200];
//            } else {
//                data = new byte[1024 * 1024 * 100];
//            }
//
////            System.out.println(data.length);
//        }

        float a = NaN;


        System.out.println(a + 1);
    }

    public static strictfp class Test {
        
    }
}
