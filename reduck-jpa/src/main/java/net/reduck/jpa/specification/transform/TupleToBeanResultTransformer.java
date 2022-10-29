package net.reduck.jpa.specification.transform;

import org.hibernate.transform.ResultTransformer;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Reduck
 * @since 2022/9/23 09:54
 */
public class TupleToBeanResultTransformer<T> implements ResultTransformer {
    private final Class<T> resultType;
    private final Map<String, Method> setterMap = new HashMap<>();

    public TupleToBeanResultTransformer(Class<T> resultType) {
        this.resultType = resultType;

        if ((!resultType.isPrimitive() && !TypeHandler.isBasicType(resultType))) {
            setterMap.putAll(SetterFinder.findSetter(resultType));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        if (resultType.isPrimitive() || TypeHandler.isBasicType(resultType)) {
            if (aliases.length > 1) {
                throw new IllegalArgumentException("Cast Exception");
            }

            if (tuple[0] instanceof BigInteger) {
                return TypeHandler.getPrimitive((BigInteger) tuple[0], resultType);
            }

            return tuple[0];
        }

        return processValue(tuple, aliases);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }

    private T processValue(Object[] tuple, String[] aliases) {
        T result = (T) ReflectUtils.newInstance(resultType);
        for (int i = 0; i < aliases.length; i++) {
            setValue(tuple[i], aliases[i], result);
        }

        return result;
    }

    private void setValue(Object data, String alias, Object target) {
        Method set = setterMap.get(alias);
        if (set != null) {
            try {
                set.invoke(target, (data instanceof BigInteger) ? ((BigInteger) data).longValue() : data);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
//
//    private T resultInstance() {
//        if (resultType.isPrimitive()) {
//            resultType.newInstance();
//        }
//    }
}
