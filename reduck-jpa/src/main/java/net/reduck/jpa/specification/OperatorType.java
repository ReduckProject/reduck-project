package net.reduck.jpa.specification;

/**
 * @author Reduck
 * @since 2019/3/8 15:20
 */
public enum OperatorType {
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
    EQUAL(" = ", "等于"),

    /**
     * 不等于
     */
    NOT_EQUAL(" != ", "不等于"),

    /**
     * 包含
     */
    CONTAIN(" LIKE '%%' ", "包含"),

    /**
     * 以...开始
     */
    BEGIN_WITH(" LIKE ", "起始位置包含"),

    /**
     * 不包含
     */
    NOT_LIKE(" NOT CONTAIN ", "不包含"),

    /**
     * 以...结束
     */
    END_WITH(" LIKE ", "结束位置包含"),

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
    String operator;
    String desc;

    OperatorType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public String getDesc() {
        return desc;
    }

    OperatorType(String operator, String desc) {
        this.operator = operator;
        this.desc = desc;
    }

    public static OperatorType getByName(String name) {
        for (OperatorType type : OperatorType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}