package net.reduck.jpa.specification;

import net.reduck.jpa.specification.annotation.SpecificationQuery;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin
 * @since 2022/8/17 11:54
 */
public class SpecificationQueryBuilder {

    private List<QueryDescriptor> descriptors = new ArrayList<>();

    public SpecificationQueryBuilder and(String property, Object value) {
        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.AND, null);
        return this;
    }

    public SpecificationQueryBuilder and(String property, Object value, OperatorType type) {
        add(property, value, type, SpecificationQuery.OperatorType.AND, null);
        return this;
    }

    public SpecificationQueryBuilder and(String property, Object value, String... join) {
        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.AND, join);
        return this;
    }

    public SpecificationQueryBuilder and(String property, Object value, OperatorType type, String... join) {
        add(property, value, type, SpecificationQuery.OperatorType.AND, join);
        return this;
    }

    public SpecificationQueryBuilder or(String property, Object value, OperatorType type, String... join) {
        add(property, value, type, SpecificationQuery.OperatorType.AND, join);
        return this;
    }

    public SpecificationQueryBuilder or(String property, Object value) {
        add(property, value, OperatorType.EQUAL, SpecificationQuery.OperatorType.OR, null);
        return this;
    }

    public SpecificationQueryBuilder or(String property, Object value, OperatorType type) {
        add(property, value, type, SpecificationQuery.OperatorType.OR, null);
        return this;
    }

    <T> Specification<T> build(Class<T> domainClass) {
        return new SpecificationResolver<>(this.descriptors, domainClass);
    }


    public SpecificationQueryBuilder newInstance() {
        return new SpecificationQueryBuilder();
    }

    private void add(String property, Object value, OperatorType type, SpecificationQuery.OperatorType operatorType, String... join) {
        QueryDescriptor descriptor = new QueryDescriptor(property, property, value, type);
        descriptor.setLinkedType(operatorType);
        descriptor.setJoinName(join);
        descriptor.setJoinType(JoinType.LEFT);
        descriptors.add(descriptor);
    }
}
