package net.reduck.core.desc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gin
 * @since 2023/2/6 16:38
 */
public class TypeHelper {
    private final static Map<Class, String> descMapping = new HashMap<>();
    private final static Map<Class, String> typeMapping = new HashMap<>();

    static {
        descMapping.put(int.class, "I");
        descMapping.put(void.class, "V");
        descMapping.put(boolean.class, "Z");
        descMapping.put(char.class, "C");
        descMapping.put(byte.class, "B");
        descMapping.put(short.class, "S");
        descMapping.put(float.class, "F");
        descMapping.put(long.class, "J");
        descMapping.put(double.class, "D");
        descMapping.put(java.util.List.class, "Ljava/util/List;");
        typeMapping.put(java.util.Collection.class, "Ljava/util/Collection;");

        typeMapping.put(int.class, "I");
        typeMapping.put(void.class, "V");
        typeMapping.put(boolean.class, "Z");
        typeMapping.put(char.class, "C");
        typeMapping.put(byte.class, "B");
        typeMapping.put(short.class, "S");
        typeMapping.put(float.class, "F");
        typeMapping.put(long.class, "J");
        typeMapping.put(double.class, "D");
    }

    public static String getDesc(Class<?> x) {
        if(descMapping.containsKey(x)) {
            return descMapping.get(x);
        }

        return "L" + getType(x) + ";";
    }

    public static String getType(Class<?> x) {
        if(typeMapping.containsKey(x)) {
            return typeMapping.get(x);
        }
        return x.getName().replace(".", "/");
    }

    public static String getGetterDesc(Class<?> x) {
        return "()" + getDesc(x);
    }

    public static String getSetterDesc(Class<?> x) {
        return "(" + getDesc(x) + ")V";
    }
}
