package net.reduck.core;

import lombok.Data;
import net.reduck.core.util.IoUtils;

/**
 * @author Gin
 * @since 2023/2/6 14:57
 */
public class Test {

    public void init(C1 c2) {
        C1 c1 = new C1();
        C1 c10 = new C1();
        C1 c11 = new C1();
        C1 c12 = new C1();
        C1 c13 = new C1();

        c10.setName(c2.getName());
        c10.setId(c2.getId());


    }
    @Data
    public static class C1 {
        String name;

        long id;

        Integer age;

        byte b;

        Byte b1;

        float f;

        Float f1;

        double d;

        double d2;

        boolean deleted;
    }

    @Data
    public static class C2 {
        String name;

        Long id;

        int age;
        boolean deleted;

        byte b;

        Byte b1;

        float f;

        Float f1;

        double d;

        double d2;
    }
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        String name = "net.reduck.Test333";
        TransformerClassBuilder builder = new TransformerClassBuilder(name, C1.class, C2.class);
        byte[] data = builder.construct().transfer().build();
        IoUtils.save(System.getProperty("user.dir") + "/Gin.class", data );



        DynamicClassLoader classLoader = new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
        Class<ObjectTransformer<C1, C2>> transformerClass = (Class<ObjectTransformer<C1, C2>> )classLoader.loadClass(name, data);
        C1 c1 = new C1();
        c1.setId(33);
        c1.setAge(12);
        c1.setB1((byte) 0x12);
        c1.setName("Gin");
//        ObjectTransformer<C1, C2> transfer = transformerClass.newInstance();
        C2 c2 = MapperUtils.map(c1,  C2.class);
//        C2 c2 = new Test333().transfer(c1);
        System.out.println(c2.toString());

    }

    public static class Test333 implements ObjectTransformer<C1,C2> {
        public Test333() {
        }

        public Test.C2 transfer(Test.C1 var1) {
            Test.C2 var2 = new Test.C2();
            var2.setName(var1.getName());
            return var2;
        }
    }
}
