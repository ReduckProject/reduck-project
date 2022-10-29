package net.reduck.jpa.dialect;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * @author Reduck
 * @since 2022/9/21 11:20
 */
public class CustomMysqlDialect extends MySQL57Dialect {

    public CustomMysqlDialect() {
        super();

//        registerColumnType(-5, "long");
        registerHibernateType(-5, "long");
    }
}
