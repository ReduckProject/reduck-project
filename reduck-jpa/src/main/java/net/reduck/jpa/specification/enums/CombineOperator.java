package net.reduck.jpa.specification.enums;

/**
 * @author Gin
 * @since 2023/5/6 11:16
 */
public
enum CombineOperator {
    /**
     * like sql `select * from t where a = ? AND b = ?`
     */
    AND,

    /**
     * like sql `select * from t where a = ? OR b = ?`
     */
    OR
}
