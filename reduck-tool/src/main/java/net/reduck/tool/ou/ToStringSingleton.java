package net.reduck.tool.ou;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author Reduck
 * @since 2022/8/24 13:34
 */
public enum ToStringSingleton {
    INSTANCE;

    public static void main(String[] args) {
        ToStringSingleton.INSTANCE.of("");
    }

    public String of(Object o) {
        try {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors())
                    .filter(descriptor -> descriptor.getReadMethod() != null)
                    .forEach(descriptor -> {
                        try {
                            sb.append(descriptor.getName()).append("=").append(descriptor.getReadMethod().invoke(o)).append("\n");
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
