package net.reduck.chat.server.test;

/**
 * @author Reduck
 * @since 2023/1/10 02:05
 */
public class DynamicTest {

    public void test(String... good){
        System.out.println(good);
    }

    public static void main(String[] args) {
        String[] data = new String[] {"1", "2", "3"};
        new DynamicTest().test(data);
    }
}
