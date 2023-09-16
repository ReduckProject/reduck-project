package net.reduck.jpa.specification.annotation;

import net.reduck.jpa.entity.transformer.ColumnTransformer;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.*;

/**
 * @author Gin
 * @since 2023/9/12 10:57
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ColumnProjection {

    String name() default "";

    String[] join() default {};

    JoinType JoinType() default JoinType.LEFT;

    Class<? extends ColumnTransformer> transformer() default ColumnTransformer.class;
}
