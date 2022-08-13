package net.reduck.jpa.specification.annotation;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.*;

/**
 * 自定义查询
 *
 * @author Reduck
 * @since 2019/4/12 11:02
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SpecificationQuery {
    /**
     * entity 属性名称
     *
     * @return
     */
    String[] property() default {};

    /**
     * 关联表
     * 支持多级关联查询
     *
     * exm:
     * 1. A 关联 B 且 B 在 A 实体属性为 {@code b}
     * 2. B 关联 C 且 C 在 B 的实体属性为 {@code c}
     * 若根据 C 的 id 属性 条件查询 A
     *
     * 则可定义注解 @SpecificationQuery(join = {"b", "c"}, property = "id")
     *
     * @return
     */
    String[] join() default {};

    /**
     * 连接方式，仅适用多表查询
     *
     * @return
     */
    JoinType joinType() default JoinType.LEFT;

    /**
     * 类型转换，若方法名不为空，则调用改方法进行类型转换作为查询值
     * 必须是一个无参方法
     *
     * @return
     */
    String castMethod() default "";

    /**
     * 动态获取忽略大小写方法
     * 必须是一个无参方法
     *
     * @return
     */
    String ignoreCaseMethod() default "";

    /**
     * not implement
     *
     * @return
     */
    // TODO: 2022/8/11
    boolean skipEmpty() default true;

    /**
     * 忽略大小写 默认不忽略
     * 优先按照{@link #ignoreCaseMethod()} 匹配
     *
     * @return
     */
    boolean ignoreCase() default false;

    /**
     * 比较关系 默认等于
     *
     * @return
     */
    net.reduck.jpa.specification.OperatorType compare() default net.reduck.jpa.specification.OperatorType.EQUAL;

    /**
     * 外部字段查询关系
     * <p>
     * 'and' or 'or'
     *
     * @return
     */
    OperatorType operator() default SpecificationQuery.OperatorType.AND;

    /**
     * 内部字段查询关系
     *
     * @return
     */
    OperatorType multiOperator() default SpecificationQuery.OperatorType.OR;

    enum OperatorType {
        /**
         * 对应数据库且查询
         */
        AND,

        /**
         * 或查询
         */
        OR
    }
}

