package net.reduck.jpa.specification.transformer;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Reduck
 * @since 2022/9/23 10:12
 */
@SuppressWarnings("unchecked")
public class TypeHandler {

    public static Object getPrimitive(BigInteger data, Class type) {
        if (data == null) {
            return null;
        }

        if (type.isAssignableFrom(Long.class)) {
            return data.longValue();
        } else if (type.isAssignableFrom(Integer.class)) {
            return data.intValue();
        }

        return data;
    }

    public static boolean isBasicType(Class<?> type){
        return ClassTypeHolder.getInstance().isBasicType(type);
    }

    public static class ClassTypeHolder {
        private static Set<Class<?>> BASIC_CLASS_TYPE = new HashSet<>();
        private static final ClassTypeHolder instance = new ClassTypeHolder();

        public ClassTypeHolder() {
            BASIC_CLASS_TYPE.add(Long.class);
            BASIC_CLASS_TYPE.add(Integer.class);
            BASIC_CLASS_TYPE.add(Short.class);
            BASIC_CLASS_TYPE.add(Float.class);
            BASIC_CLASS_TYPE.add(Double.class);
            BASIC_CLASS_TYPE.add(Character.class);
            BASIC_CLASS_TYPE.add(Byte.class);
            BASIC_CLASS_TYPE.add(Void.class);
            BASIC_CLASS_TYPE.add(Boolean.class);
        }

        public boolean isBasicType(Class<?> type) {
            return BASIC_CLASS_TYPE.contains(type);
        }

        static ClassTypeHolder getInstance(){
            return instance;
        }
    }
}
