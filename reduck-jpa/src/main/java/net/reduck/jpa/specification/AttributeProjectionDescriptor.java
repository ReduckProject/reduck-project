package net.reduck.jpa.specification;



import net.reduck.jpa.specification.enums.CombineOperator;
import net.reduck.jpa.specification.enums.CompareOperator;

import javax.persistence.criteria.JoinType;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Reduck
 * @since 2019/6/10 17:23
 */
public class AttributeProjectionDescriptor {
    AttributeProjectionDescriptor(String columnName, String name, Object value, CompareOperator operatorType) {
        this.columnName = columnName;
        this.name = name;
        this.value = value;
        this.operatorType = operatorType;
    }

    public AttributeProjectionDescriptor(String name, Object value, CompareOperator operatorType) {
        this.name = name;
        this.value = value;
        this.operatorType = operatorType;
    }

    /**
     * 实体对应的列名
     */
    String columnName;

    /**
     * 查询条件的名称
     */
    String name;

    /**
     * 内部查询条件
     */
    Set<String> innerNames = new HashSet<>();

    /**
     * 条件对应值，须和实体列类型一一对应
     */
    Object value;

    /**
     * 操作符，大于、小于 .et
     */
    CompareOperator operatorType;

    boolean ignoreCase;

    String[] joinName;

    JoinType joinType;

    /**
     * 外部查询关联
     */
    CombineOperator combine = CombineOperator.AND;

    /**
     * 内部查询关联
     */
    CombineOperator innerCombine = CombineOperator.AND;

    void setCombine(CombineOperator combine) {
        this.combine = combine;
    }

    void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setJoinName(String[] joinName) {
        this.joinName = joinName;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }
}
