package net.reduck.jpa.specification.annotation;

import java.lang.annotation.*;

/**
 * select distinct a.id as a_id ... from A as a;
 *
 * @author Reduck
 * @since 2020/1/6 16:34
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Distinct {
    boolean value() default true;

    /**
     * 判断是否对结果进行 distinct 处理
     *
     * @return distinct method name， 此method返回boolean对象
     */
    String distinctMethod() default "";
}
