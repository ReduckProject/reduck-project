package net.reduck.core;

/**
 * @author Gin
 * @since 2023/2/3 16:18
 */
public interface ObjectTransformer<T, R> {
    R transfer(T original);

}
