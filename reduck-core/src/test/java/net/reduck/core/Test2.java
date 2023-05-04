package net.reduck.core;

import lombok.Data;

/**
 * @author Gin
 * @since 2023/2/7 10:50
 */
public class Test2 {
    @Data
    public static class C1 {
        int age;
    }

    @Data
    public static class C2 {
        Integer age;
    }

    public void map() {
        C1 c1 = new C1();
        c1.setAge(15);
        C2 c2 = new C2();
        c2.setAge(15);

        System.out.println("long2Long");
        c2.setAge(c1.getAge());

        System.out.println("Long2long");
        c1.setAge(c2.getAge());
    }
}
