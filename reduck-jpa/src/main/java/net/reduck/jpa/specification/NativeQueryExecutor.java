package net.reduck.jpa.specification;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Reduck
 * @since 2022/10/18 15:19
 */
public class NativeQueryExecutor {
    private final JdbcTemplate jdbcTemplate;

    public NativeQueryExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> query(String sql, Class<T> resultType, Object... params) {
        return jdbcTemplate.query(sql, instanceRowMapper(resultType), params);
    }

    @SuppressWarnings("all")
    private <T> RowMapper<T> instanceRowMapper(Class<T> expectType) {

        return (RowMapper<T>) Proxy.newProxyInstance(RowMapper.class.getClassLoader()
                , new Class[]{RowMapper.class}
                , new InvocationHandler() {
                    private Map<Method, String> propertyMap = null;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("mapRow")) {
                            if (propertyMap == null) {
                                initColumnMap((ResultSet) args[0]);
                            }
                            T instance = expectType.newInstance();
                            for (Map.Entry<Method, String> entry : propertyMap.entrySet()) {
                                invokeWriteMethod(entry.getKey(), instance, (ResultSet) args[0], entry.getValue());
                                System.out.println();
                            }
                            return instance;
                        }
                        return null;
                    }

                    private synchronized void initColumnMap(ResultSet rs) throws SQLException, IntrospectionException {
                        if (propertyMap == null) {
                            propertyMap = new HashMap<>();
                        } else {
                            return;
                        }

                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        Map<String, String> columnMap = new HashMap<>();
                        for (int i = 0; i < columnCount; i++) {
                            columnMap.put(normalize(metaData.getColumnName(i + 1)), metaData.getColumnName(i + 1));
                        }

                        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(expectType).getPropertyDescriptors();
                        for (PropertyDescriptor descriptor : descriptors) {
                            if (!columnMap.containsKey(normalize(descriptor.getName())) || descriptor.getWriteMethod() == null) {
                                continue;
                            }
                            propertyMap.put(descriptor.getWriteMethod(), columnMap.get(normalize(descriptor.getName())));
                        }
                    }

                    private String normalize(String name) {
                        return name.replace("_", "").toLowerCase();
                    }

                    private void invokeWriteMethod(Method method, Object instance, ResultSet rs, String columnName) throws SQLException, InvocationTargetException, IllegalAccessException {
                        Class type = method.getParameterTypes()[0];
                        switch (type.getName()) {
                            case "java.lang.String":
                                method.invoke(instance, rs.getString(columnName));
                                return;
                            case "long":
                            case "java.lang.Long":
                                method.invoke(instance, rs.getLong(columnName));
                                return;
                            case "int":
                            case "java.lang.Integer":
                                method.invoke(instance, rs.getInt(columnName));
                                return;
                            case "byte":
                            case "java.lang.Byte":
                                method.invoke(instance, rs.getByte(columnName));
                                return;
                            case "short":
                            case "java.lang.Short":
                                method.invoke(instance, rs.getShort(columnName));
                                return;
                            case "float":
                            case "java.lang.Float":
                                method.invoke(instance, rs.getFloat(columnName));
                                return;
                            case "double":
                            case "java.lang.Double":
                                method.invoke(instance, rs.getDouble(columnName));
                            case "boolean":
                            case "java.lang.Boolean":
                                method.invoke(instance, rs.getDouble(columnName));
                                return;
                            case "char":
                            case "java.lang.Character":
                                // should not go to here
                                return;
                            case "java.math.BigDecimal":
                                method.invoke(instance, rs.getBigDecimal(columnName));
                                return;
                            case "java.sql.Time":
                                method.invoke(instance, rs.getTime(columnName));
                                return;

                            default:
                                throw new RuntimeException("Unsupported type of " + type.getName());

                        }
                    }
                }
        );
    }
}
