package net.reduck.jpa.specification.annotation;

import java.lang.annotation.*;

/**
 * jpa 对GroupBy分页支持不是很友好，故弃用
 *
 * @author Reduck
 * @since 2020/1/6 15:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Deprecated
public @interface GroupBy {
    String[] properties() default "id";
}
