package net.reduck.core;

/**
 * @author Gin
 * @since 2023/2/7 10:35
 */
public class MapperUtils {
    static MapperClassLoader classLoader = new MapperClassLoader(Thread.currentThread().getContextClassLoader());

    @SuppressWarnings("unchecked")
    public static <T, R> R map(T x, Class<R> targetType) {
        try {
            ObjectTransformer<T, R> transformer = (ObjectTransformer<T, R>)(classLoader.loadClass(x.getClass(), targetType).newInstance());
            return transformer.transfer(x);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
