package net.reduck.jpa.specification.transform;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Reduck
 * @since 2022/9/23 10:56
 */
public class SetterFinder {

    private final Map<String, Method> setterMap = new HashMap<>();

    public static Map<String, Method> findSetter(Class type){
        Map<String, Method> setterMap = new HashMap<>();
        PropertyDescriptor[] descriptors;
        try {
            descriptors = Introspector.getBeanInfo(type).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        for(PropertyDescriptor descriptor : descriptors){
            setterMap.put(descriptor.getName(), descriptor.getWriteMethod());
            setterMap.put(NameHandler.withCase(descriptor.getName()), descriptor.getWriteMethod());
            setterMap.put(NameHandler.withLine(descriptor.getName()), descriptor.getWriteMethod());
        }

        return setterMap;
    }
}
