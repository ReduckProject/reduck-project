package net.reduck.jpa.specification.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 将给的格式的日期转换为 时间戳形式
 *
 * Long timestamp = formatter(pattern) + difference * timeUnit
 *
 * @author Reduck
 * @since 2019/12/13 10:38
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Date {
    /**
     * 转换规则
     *
     * @return
     */
    String pattern() default "yyyy-MM-dd";

    /**
     * 时差 +
     *
     * @return
     */
    int difference() default 0;

    /**
     * 单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.DAYS;
}
