package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.SpecificationQuery;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Reduck
 * @since 2022/8/17 11:54
 */
public class SpecificationQueryBuilder {

    private List<PredicateDescriptor> descriptors = new ArrayList<>();

//    public SpecificationQueryBuilder and(String property, Object value) {
//        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.AND, null);
//        return this;
//    }
//
//    public SpecificationQueryBuilder and(String property, Object value, OperatorType type) {
//        add(property, value, type, SpecificationQuery.OperatorType.AND, null);
//        return this;
//    }
//
//    public SpecificationQueryBuilder and(String property, Object value, String... join) {
//        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.AND, join);
//        return this;
//    }
//
//    public SpecificationQueryBuilder and(String property, Object value, OperatorType type, String... join) {
//        add(property, value, type, SpecificationQuery.OperatorType.AND, join);
//        return this;
//    }
//
//    public SpecificationQueryBuilder or(String property, Object value, OperatorType type, String... join) {
//        add(property, value, type, SpecificationQuery.OperatorType.AND, join);
//        return this;
//    }
//
//    public SpecificationQueryBuilder or(String property, Object value) {
//        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.OR, null);
//        return this;
//    }
//
//    public SpecificationQueryBuilder or(String property, Object value, OperatorType type) {
//        add(property, value, type, SpecificationQuery.OperatorType.OR, null);
//        return this;
//    }

    public static SpecificationQueryBuilder newInstance() {
        return new SpecificationQueryBuilder();
    }

    <T> Specification<T> build(Class<T> domainClass) {
        return new SpecificationResolver<>(this.descriptors, domainClass);
    }

    private void add(String property, Object value, CompareOperator type, SpecificationQuery.BooleanOperator operatorType, String... join) {
        PredicateDescriptor descriptor = new PredicateDescriptor(property, property, value, type);
        descriptor.setCombined(operatorType);
        if (join != null) {
            descriptor.setJoinName(join);
            descriptor.setJoinType(JoinType.LEFT);
        }
        descriptors.add(descriptor);
    }

    void add2(String property, Object value, CompareOperator type, SpecificationQuery.BooleanOperator operatorType, List<String> joins) {
        PredicateDescriptor descriptor = new PredicateDescriptor(property, property, value, type);
        descriptor.setCombined(operatorType);
        if (joins != null && joins.size() > 0) {
            descriptor.setJoinName(joins.toArray(new String[0]));
            descriptor.setJoinType(JoinType.LEFT);
        }
        descriptors.add(descriptor);
    }

    public Matcher and(String property) {
        return new Matcher(this, property, SpecificationQuery.BooleanOperator.AND);
    }

    public Matcher or(String property) {
        return new Matcher(this, property, SpecificationQuery.BooleanOperator.OR);
    }

    public static class Matcher {
        private final SpecificationQueryBuilder builder;
        private String property;
        private Object value;
        private CompareOperator type = CompareOperator.EQUALS;
        private SpecificationQuery.BooleanOperator operatorType = SpecificationQuery.BooleanOperator.AND;
        private List<String> joins = new LinkedList<>();
        private JoinType joinType = JoinType.LEFT;

        public Matcher(SpecificationQueryBuilder builder) {
            this.builder = builder;
        }

        public Matcher(SpecificationQueryBuilder builder, String property, SpecificationQuery.BooleanOperator operatorType) {
            this.builder = builder;
            this.operatorType = operatorType;
            this.property = property;
        }

        private Matcher and() {
            operatorType = SpecificationQuery.BooleanOperator.AND;
            return this;
        }

        private Matcher or() {
            operatorType = SpecificationQuery.BooleanOperator.OR;
            return this;
        }

        public Matcher operate(CompareOperator type) {
            this.type = type;
            return this;
        }

        public Matcher property(String property) {
            this.property = property;
            return this;
        }

        public Matcher value(Object value) {
            this.value = value;
            return this;
        }

        public Matcher joins(String... join) {
            if (join != null) {
                this.joins.addAll(Arrays.stream(join).collect(Collectors.toList()));
            }

            return this;
        }

        public SpecificationQueryBuilder match() {
            this.builder.add2(property, value, type, operatorType, joins);
            return this.builder;
        }
    }
}
