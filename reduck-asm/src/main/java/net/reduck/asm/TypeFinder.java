package net.reduck.asm;

import java.lang.reflect.Type;

/**
 * @author Reduck
 * @since 2022/8/31 17:14
 */
public class TypeFinder<T> extends GenericEntity<T>{
    public TypeFinder(T entity) {
        super(entity);
    }

    public TypeFinder(T entity, Type genericType) {
        super(entity, genericType);
    }
}
