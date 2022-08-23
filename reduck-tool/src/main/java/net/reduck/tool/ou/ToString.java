package net.reduck.tool.ou;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author Gin
 * @since 2022/8/23 11:33
 */
public class ToString {


    public static String of(Object o) {
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
