package net.reduck.jpa.specification.enums;

/**
 * @author Reduck
 * @since 2019/3/8 15:20
 */
public enum CompareOperator {
    /**
     * 大于
     */
    GRATER_THAN(" > ", "大于"),

    /**
     * 大于等于
     */
    GRATER_THAN_OR_EQUAL(" >= ", "大于等于"),

    /**
     * 小于
     */
    LESS_THAN(" < ", "小于"),

    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL(" <=", "小于等于"),

    /**
     * 等于
     */
    EQUALS(" = ", "等于"),

    /**
     * 不等于
     */
    NOT_EQUALS(" != ", "不等于"),

    /**
     * 包含
     */
    CONTAINS(" LIKE '%%' ", "包含"),

    /**
     * 以...开始
     */
    STARTS_WITH(" LIKE ", "起始位置包含"),

    /**
     * 不包含
     */
    NOT_CONTAINS(" NOT CONTAIN ", "不包含"),

    /**
     * 以...结束
     */
    ENDS_WITH(" LIKE ", "结束位置包含"),

    /**
     * 都不是
     */
    NOT_IN(" NOT IN() ", "都不是"),

    /**
     * 任意
     */
    IN(" IN()", "任一"),

    /**
     * null or ''
     */
    NULL(" IS NULL", "为空"),

    NOT_NULL(" NOT NULL ", "不为空")

    ;
    final String operator;
    String desc;

    CompareOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public String getDesc() {
        return desc;
    }

    CompareOperator(String operator, String desc) {
        this.operator = operator;
        this.desc = desc;
    }

    public static CompareOperator getByName(String name) {
        for (CompareOperator type : CompareOperator.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}