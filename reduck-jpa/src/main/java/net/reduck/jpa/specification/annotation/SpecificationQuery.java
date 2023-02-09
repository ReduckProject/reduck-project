package net.reduck.jpa.specification.annotation;

import net.reduck.jpa.specification.CompareOperator;

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
     * 关联查询类型，仅当需要进行关联查询时才生效
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
     * 例如：
     * class Query {
     *     void ignoreCase() {
     *         return false;
     *     }
     * }
     *
     * @return
     */
    String ignoreCaseMethod() default "";

    /**
     * Is ignore case
     * Can be override by {@link #ignoreCaseMethod()}
     *
     * @return
     */
    boolean ignoreCase() default false;

    /**
     * Compare operator default {@link CompareOperator#EQUALS}
     *
     * @return
     */
    CompareOperator compare() default CompareOperator.EQUALS;

    /**
     * 外部字段查询关系
     * <p>
     * 'and' or 'or'
     *
     * @return
     */
    BooleanOperator operator() default BooleanOperator.AND;

    /**
     * 内部字段查询关系
     *
     * @return
     */
    BooleanOperator multiOperator() default BooleanOperator.OR;

    /**
     * Detect is valid parameter
     *
     * @return
     */
    Matches match() default Matches.NOT_EMPTY;

    enum BooleanOperator {
        /**
         * like sql `select * from t where a = ? AND b = ?`
         */
        AND,

        /**
         * like sql `select * from t where a = ? OR b = ?`
         */
        OR
    }

    enum Matches {
        NOT_EMPTY,

        NOT_NULL,

        NOT_ZERO,

        ALWAYS;
    }
}

