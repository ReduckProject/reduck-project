package net.reduck.asm.transfer;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gin
 * @since 2019/4/11 15:51
 */
@SuppressWarnings({"Duplicates"})
public class MapperUtils {
    private static final Map<String, Map<String, PropertyDescriptor>> cache = new ConcurrentHashMap<>(128);

    /**
     * 简单的get、set遍历转换
     * <p>
     * 转换深度为1，内部类和内部集合类都当作普通field处理
     * 源类名称与目标类名称一致时，类型必须定义成一致
     *
     * @param source 源类
     * @param target 目标类
     */
    public static void map(Object source, Object target, Class<?> exclude) {
        Map<String, PropertyDescriptor> sourcePropertiesMap = getProperties(source.getClass());
        Map<String, PropertyDescriptor> targetPropertiesMap = getProperties(target.getClass());
        Map<String, PropertyDescriptor> excludePropertiesMap = exclude == null ? new HashMap<>() : getProperties(exclude);

        sourcePropertiesMap.forEach((k, v) -> {
            if (!excludePropertiesMap.containsKey(k)
                    && targetPropertiesMap.containsKey(k)
                    && targetPropertiesMap.get(k).getWriteMethod() != null
                    && v.getReadMethod() != null) {

                try {
                    targetPropertiesMap.get(k).getWriteMethod().invoke(target, v.getReadMethod().invoke(source));
                } catch (Exception e) {
                    throw new RuntimeException(
                            targetPropertiesMap.get(k).getWriteMethod().getName()
                                    + " method argument type error: expect type is "
                                    + targetPropertiesMap.get(k).getPropertyType().getSimpleName()
                                    + ", and actual type is "
                                    + v.getPropertyType().getSimpleName());
                }
            }
        });
    }

    public static void map(Object source, Object target) {
        map(source, target, null);
    }

    public static <T> T map(Object source, Class<T> targetClass, Class<?> exclude) {
        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        map(source, target, exclude);
        return target;

    }

    public static <T> T map(Object source, Class<T> targetClass) {
        return map(source, targetClass, null);
    }

    public static Object invokeGet(String name, Object o) {
        if (o == null) {
            return null;
        }

        for (Method method : o.getClass().getMethods()) {
            if (method.getName().equals("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
                    || method.getName().equals("is" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
            ) {
                try {
                    return method.invoke(o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    /**
     * 获取类的properties属性
     *
     * @param target 目标类
     *
     * @return
     */
    static Map<String, PropertyDescriptor> getProperties(Class<?> target) {
        if (cache.containsKey(target.getName())) {
            return cache.get(target.getName());
        }

        System.out.println("3333333333333333333");
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<String, PropertyDescriptor> propertyDescriptorMap = new HashMap<>();

        Arrays.asList(propertyDescriptors)
                .forEach(propertyDescriptor -> propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor));

        // remove Object#getClass
        propertyDescriptorMap.remove("class");

        cache.put(target.getName(), propertyDescriptorMap);
        return propertyDescriptorMap;

    }
}
