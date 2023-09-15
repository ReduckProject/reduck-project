package net.reduck.jpa.entity.transformer;

/**
 * @author Gin
 * @since 2023/9/7 11:25
 */
@FunctionalInterface
public interface AttributeTransformer<T, R> {

     R transform(T attribute);
}
