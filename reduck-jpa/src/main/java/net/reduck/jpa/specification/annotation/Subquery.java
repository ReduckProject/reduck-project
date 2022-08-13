package net.reduck.jpa.specification.annotation;

import java.lang.annotation.*;

/**
 * 用于查询类上，标识类下边所有条件作为子语句
 * 假如查询 {@code A} 实体最终SQL形如：
 * {@code select a1.* from A as a1 where A.id in (select a2.id from A where ... )}
 *
 * @author Reduck
 * @since 2019/11/20 20:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Subquery {
    /**
     * 指定当哪些 properties 存在时进行子查询，若不指定则默认所有
     *
     * @return
     */
    String[] properties() default {};
}
