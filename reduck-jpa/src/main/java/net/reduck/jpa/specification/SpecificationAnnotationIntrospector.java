package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.Date;
import net.reduck.jpa.specification.annotation.SpecificationIgnore;
import net.reduck.jpa.specification.annotation.SpecificationQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Reduck
 * @since 2019/6/10 17:13
 */
class SpecificationAnnotationIntrospector {
    private static final String EXCLUDE_PROPERTY = "class";

    /**
     * 将注解转换为自定义查询条件
     *
     * @param o
     *
     * @return
     */
    static List<QueryDescriptor> getQueryDescriptors(Object o) {
        List<QueryDescriptor> customConditions = new ArrayList<>();
        Map<String, Method> properties = getPropertiesAndGetter(o.getClass());

        // 仅获取当前类的属性，不获取父类属性
        Field[] fields = o.getClass().getDeclaredFields();

        Set<String> ignoreSet = new HashSet<>();
        Map<String, Annotations> queryMap = new HashMap<>();

        //
        for (Field field : fields) {
            if (!properties.containsKey(field.getName())) {
                continue;
            }

            if (field.getAnnotation(SpecificationIgnore.class) != null) {
                ignoreSet.add(field.getName());
                continue;
            }

            queryMap.put(field.getName(), new Annotations(field.getAnnotation(SpecificationQuery.class), field.getAnnotation(Date.class)));
        }

        for (Map.Entry<String, Method> entry : properties.entrySet()) {
            // filed 标有忽略注解或者查询注解 不再进行处理
            if (ignoreSet.contains(entry.getKey())) {
                continue;
            }

            if (queryMap.get(entry.getKey()) != null) {
                continue;
            }

            // property 标有忽略注解不进行处理，此处可不添加到  ignoreSet里
            if (AnnotationUtils.findAnnotation(entry.getValue(), SpecificationIgnore.class) != null) {
                queryMap.remove(entry.getKey());
                continue;
            }

            queryMap.put(entry.getKey()
                    , new Annotations(AnnotationUtils.findAnnotation(entry.getValue(), SpecificationQuery.class)
                            , AnnotationUtils.findAnnotation(entry.getValue(), Date.class)));
        }

        for (Map.Entry<String, Annotations> entry : queryMap.entrySet()) {
            if (ignoreSet.contains(entry.getKey())) {
                continue;
            }

            addDescriptor(entry.getValue(), entry.getKey(), properties.get(entry.getKey()), o, customConditions);
            ignoreSet.add(entry.getKey());
        }

        return customConditions;
    }

    static void addDescriptor(Annotations annotations, String name, Method method, Object target, List<QueryDescriptor> descriptors) {
        Object value = invoke(method, target);

        if (annotations.query != null) {
            SpecificationQuery query = annotations.query;
            // Arrays.asList()转化后是个内部的List,且未实现add方法，故长度固定
            List<String> columns = new ArrayList<>(Arrays.asList(query.property()));
            if (columns.size() == 0) {
                columns.add(name);
            }

            if (!"".equals(query.castMethod())) {
                value = invoke(Objects.requireNonNull(BeanUtils.findMethod(target.getClass(), query.castMethod())), target);
            }

            // null 值忽略
            if (value == null) {
                return;
            }

            // 空字符串忽略
            if (value instanceof String) {
                if ("".equals(value)) {
                    return;
                }
            }

            // 空集合忽略
            if (value instanceof Collection) {
                if (((Collection<?>) value).isEmpty()) {
                    return;
                }
            }

            if (annotations.date != null) {
                value = getTime(annotations.date, value.toString());
            }

            int i = 1;
            QueryDescriptor customCondition = null;
            for (String columnName : columns) {
                if (i == 1) {
                    customCondition = new QueryDescriptor(columnName, name, value, query.compare());
                    if ("".equals(query.ignoreCaseMethod())) {
                        customCondition.setIgnoreCase(query.ignoreCase());
                    } else {
                        Boolean ignoreCase = (Boolean) invoke(Objects.requireNonNull(BeanUtils.findMethod(target.getClass(), query.ignoreCaseMethod())), target);
                        customCondition.setIgnoreCase(ignoreCase != null && ignoreCase);
                    }
                    customCondition.setLinkedType(query.operator());
                    customCondition.setJoinName(query.join());
                    customCondition.setJoinType(query.joinType());
                    descriptors.add(customCondition);
                } else {
                    customCondition.inNames.add(columnName);
                    customCondition.inLinkedType = query.multiOperator();
                }
                i++;
            }
        } else {
            if (value == null) {
                return;
            }

            if (annotations.date != null) {
                value = getTime(annotations.date, value.toString());
            }

            if (value instanceof String) {
                if ("".equals(value)) {
                    return;
                }
            }

            descriptors.add(new QueryDescriptor(name, name, invoke(method, target), OperatorType.EQUAL));
        }
    }

    static Object invoke(Method method, Object target) {
        try {
            return method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 properties 及对应的getter方法
     *
     * @param target
     *
     * @return
     */
    static Map<String, Method> getPropertiesAndGetter(Class<?> target) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<String, Method> getterMap = new HashMap<>();

        Arrays.asList(propertyDescriptors)
                .forEach(propertyDescriptor -> getterMap.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod()));

        // remove Object#getClass
        getterMap.remove(EXCLUDE_PROPERTY);

        return getterMap;

    }

    static Map<String, PropertyDescriptor> getPropertyDescriptor(Class<?> target) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        return Arrays.stream(propertyDescriptors)
                .filter(descriptor -> !EXCLUDE_PROPERTY.equals(descriptor.getName()))
                .collect(Collectors.toMap(FeatureDescriptor::getName, e -> e));
    }

    static List<String> getAttributes(Class<?> target) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        return Arrays.stream(propertyDescriptors)
                .filter(propertyDescriptor -> !EXCLUDE_PROPERTY.equals(propertyDescriptor.getName()))
                .map(FeatureDescriptor::getName).collect(Collectors.toList());
    }

    protected static class Annotations {
        public Annotations(SpecificationQuery query, Date date) {
            this.query = query;
            this.date = date;
        }

        SpecificationQuery query;

        Date date;
    }

    static Long getTime(Date date, String value) {
        return dateFormat(value, date.pattern()) + date.timeUnit().toMillis(date.difference());
    }

    static long dateFormat(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date).getTime();
        } catch (ParseException e) {
            throw new RuntimeException("Date formatter is not correct , expected is " + pattern + " but actual is " + date);
        }
    }

}
