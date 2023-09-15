package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.AttributeIgnore;
import net.reduck.jpa.specification.annotation.ColumnProjection;
import org.springframework.core.annotation.AnnotationUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gin
 * @since 2023/9/12 11:28
 */
public class AttributeProjectionParser {

    public static List<ColumnProjectionDescriptor> parse(Class type) {
        if (type == null) {
            throw new ReduckException("响应类型不能为空");
        }

        Map<String, PropertyDescriptor> descriptorMap = getBeanInfo(type);
        Map<String, ColumnProjectionDescriptor> projectionDescriptorMap = descriptorMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ColumnProjectionDescriptor()
                        .setFieldName(entry.getKey())
                        .setDescriptor(entry.getValue()))
                );

        parser(type, descriptorMap, projectionDescriptorMap);

        return new ArrayList<>(projectionDescriptorMap.values());
    }

    private static void parser(Class type, Map<String, PropertyDescriptor> descriptorMap, Map<String, ColumnProjectionDescriptor> projectionDescriptorMap) {
        if (type != Object.class) {
            parser((Class) type.getGenericSuperclass(), descriptorMap, projectionDescriptorMap);
        }

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor propertyDescriptor = descriptorMap.get(field.getName());

            // 忽略该属性
            if (AnnotationUtils.findAnnotation(propertyDescriptor.getReadMethod(), AttributeIgnore.class) != null
                    || field.getAnnotationsByType(AttributeIgnore.class).length > 0) {
                projectionDescriptorMap.remove(field.getName());
                continue;
            }

            // 属性映射
            ColumnProjection columnProjection = AnnotationUtils.findAnnotation(propertyDescriptor.getReadMethod(), ColumnProjection.class);
            if(columnProjection == null) {
                ColumnProjection[] projections = field.getAnnotationsByType(ColumnProjection.class);
                if(projections.length > 0) {
                    columnProjection = projections[0];
                }
            }
            // 属性映射
            if (columnProjection != null) {
                if (projectionDescriptorMap.containsKey(field.getName())) {
                    ColumnProjectionDescriptor columnProjectionDescriptor = projectionDescriptorMap.get(field.getName());
                    columnProjectionDescriptor.setName(columnProjection.name())
                            .setJoin(columnProjection.join())
                            .setTransformer(columnProjection.transformer());
                }
            }
        }
    }

    private static Map<String, PropertyDescriptor> getBeanInfo(Class pojoType) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(pojoType).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        return Arrays.stream(propertyDescriptors)
                .filter(propertyDescriptor -> propertyDescriptor.getWriteMethod() != null && propertyDescriptor.getReadMethod() != null)
                .collect(Collectors.toMap(PropertyDescriptor::getName, propertyDescriptor -> propertyDescriptor));
    }
}
