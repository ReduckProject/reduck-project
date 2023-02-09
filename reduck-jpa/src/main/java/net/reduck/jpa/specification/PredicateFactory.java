package net.reduck.jpa.specification;

import lombok.extern.slf4j.Slf4j;
import net.reduck.jpa.specification.annotation.SpecificationQuery;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Gin
 * @since 2023/2/7 17:51
 */
@Slf4j
public class PredicateFactory<T> {
    private final PredicateInfo predicateInfo;

    private final Root<T> root;
    private final CriteriaQuery<?> query;
    private final CriteriaBuilder criteriaBuilder;

    private final EscapeCharacter escape = EscapeCharacter.DEFAULT;

    private final Map<String, Join> joinCache = new HashMap<>();

    public PredicateFactory(PredicateInfo predicateInfo, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        this.predicateInfo = predicateInfo;
        this.root = root;
        this.query = query;
        this.criteriaBuilder = criteriaBuilder;
    }

    public Predicate build() {
        processDistinct();
        Predicate predicate = processConditions();
        return predicate;
    }

    public void processDistinct() {
        query.distinct(predicateInfo.isDistinct());
    }

    public Predicate processConditions() {
        Predicate predicate = criteriaBuilder.conjunction();

        // 自定义条件转换
        if (predicateInfo.getDescriptors() != null) {
            for (PredicateDescriptor condition : predicateInfo.getDescriptors()) {

                // 处理 like 多值
                Predicate nextPredicate = getMultipleValuePredicate(condition, criteriaBuilder, root);
                // 处理非like多值
                if (nextPredicate == null) {
                    nextPredicate = processPredicateCondition(condition);
                    nextPredicate = getInPredicate(nextPredicate, condition, criteriaBuilder, root);
                }

                if(condition.combined == SpecificationQuery.BooleanOperator.AND) {
                    predicate = criteriaBuilder.and(nextPredicate);
                }

                if(condition.combined == SpecificationQuery.BooleanOperator.OR) {
                    predicate = criteriaBuilder.or(nextPredicate);
                }
            }
        }
        return predicate;
    }

    public Predicate processPredicateCondition(PredicateDescriptor condition) {
        Predicate predicate;
        switch (condition.operatorType) {
            // 小于等于
            case LESS_THAN_OR_EQUAL:
                predicate = criteriaBuilder.lessThanOrEqualTo(root.get(condition.columnName), (Comparable) condition.value);
                break;

            // 包含
            case CONTAINS:
                if (condition.ignoreCase) {
                    predicate = (criteriaBuilder.like(criteriaBuilder.lower(getPath(condition, root)), "%" + escape(((String) condition.value).toLowerCase()) + "%"));
                } else {
                    predicate = criteriaBuilder.like(getPath(condition, root), "%" + escape(String.valueOf(condition.value)) + "%");
                }
                break;

            // 小于
            case LESS_THAN:
                predicate = criteriaBuilder.lessThan(getPath(condition, root), (Comparable) condition.value);
                break;

            // 大于
            case GRATER_THAN:
                predicate = criteriaBuilder.greaterThan(getPath(condition, root), (Comparable) condition.value);
                break;

            // 大于等于
            case GRATER_THAN_OR_EQUAL:
                predicate = criteriaBuilder.greaterThanOrEqualTo(getPath(condition, root), (Comparable) condition.value);
                break;

            // 不等于
            case NOT_EQUALS:
                predicate = criteriaBuilder.notEqual(getPath(condition, root), condition.value);
                break;

            case STARTS_WITH:
                predicate = criteriaBuilder.notLike(getPath(condition, root), condition.value + "%");
                break;

            case ENDS_WITH:
                predicate = criteriaBuilder.notLike(getPath(condition, root), "%" + condition.value);
                break;

            case NULL:
                predicate = criteriaBuilder.isNull(getPath(condition, root));
                break;

            case NOT_NULL:
                predicate = criteriaBuilder.isNotNull(getPath(condition, root));
                break;

            case IN:
                predicate = criteriaBuilder.in(getPath(condition, root));
                if (condition.value instanceof Collection) {
                    Collection collection = (Collection) condition.value;
                    for (Object o : collection) {
                        ((CriteriaBuilder.In) predicate).value(o);
                    }
                }
                break;

            case NOT_IN:
                predicate = criteriaBuilder.in(getPath(condition, root));
                if (condition.value instanceof Collection) {
                    Collection collection = (Collection) condition.value;
                    for (Object o : collection) {
                        ((CriteriaBuilder.In) predicate).value(o);
                    }
                }

                predicate = predicate.not();
                break;

            // 默认等于
            case EQUALS:
            default:
                predicate = criteriaBuilder.equal(getPath(condition, root), condition.value);
                break;
        }

        return predicate;
    }

    private Path getPath(PredicateDescriptor condition, Root<T> root) {
        if (condition.joinName == null || condition.joinName.length == 0) {
            return root.get(condition.columnName);
        }

        return getJoin(root, condition.joinName, condition.joinType).get(condition.columnName);
    }

    /**
     * 获取关联表
     * <p>
     * root.getJoins().add(...)
     * 后续可使用 root本身 不用新建一个Map,解析 查询可能会比较麻烦
     *
     * @param root
     * @param joinNames
     *
     * @return
     */
    private Join getJoin(Root<?> root, String[] joinNames, JoinType joinType) {
//        root.getJoins().
        StringBuilder sb = new StringBuilder();
        Join join = null;

        for (String joinName : joinNames) {
            sb.append(joinName).append(".");
            if (!joinCache.containsKey(sb.toString())) {
                if (join == null) {
                    joinCache.put(sb.toString(), root.join(joinName, joinType));
                } else {
                    joinCache.put(sb.toString(), join.join(joinName, joinType));
                }
            }
            join = joinCache.get(sb.toString());
        }
        return join;
    }

    /**
     * 模糊查询时进行字符转义
     *
     * @param value 待转义字符
     *
     * @return
     */
    private String escape(String value) {
        return StringUtils.hasText(value) ? escape.escape(value) : value;
    }

    private Predicate getMultipleValuePredicate(PredicateDescriptor condition, CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (condition.operatorType != CompareOperator.ENDS_WITH
                && condition.operatorType != CompareOperator.CONTAINS
                && condition.operatorType != CompareOperator.STARTS_WITH
        ) {
            return null;
        }

        if (!(condition.value instanceof Collection)) {
            return null;
        }

        List<Predicate> predicates = new ArrayList<>();
        for (Object values : (Collection) condition.value) {
            PredicateDescriptor newCondition = new PredicateDescriptor(condition.name, condition.name, values, condition.operatorType);
            newCondition.setJoinName(condition.joinName);
            newCondition.setJoinType(condition.joinType);
            predicates.add(processPredicateCondition(newCondition));
        }

        return condition.multiCombined == SpecificationQuery.BooleanOperator.OR
                ? criteriaBuilder.or(criteriaBuilder.or(predicates.toArray(new Predicate[]{})))
                : criteriaBuilder.and(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
    }

    private Predicate getInPredicate(Predicate predicate, PredicateDescriptor condition, CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (condition.inNames.size() == 0) {
            return predicate;
        }

        List<Predicate> predicates = new ArrayList<>();
        for (String name : condition.inNames) {
            PredicateDescriptor newCondition = new PredicateDescriptor(name, name, condition.value, condition.operatorType);
            newCondition.setJoinName(condition.joinName);
            newCondition.setJoinType(condition.joinType);
            predicates.add(processPredicateCondition(newCondition));
        }

        predicates.add(predicate);

        switch (condition.multiCombined) {
            case AND:
                return criteriaBuilder.and(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

            case OR:
                return criteriaBuilder.or(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

            default:
                throw new IllegalArgumentException();
        }
    }
}

