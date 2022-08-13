package net.reduck.jpa;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

@Slf4j
public class DynamicTableNameInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        return sql;
    }
}
