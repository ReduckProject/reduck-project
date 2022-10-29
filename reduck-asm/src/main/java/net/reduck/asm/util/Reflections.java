package net.reduck.asm.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Reduck
 * @since 2022/8/31 16:31
 */
public class Reflections {

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }
}
