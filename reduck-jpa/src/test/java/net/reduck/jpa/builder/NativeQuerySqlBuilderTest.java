package net.reduck.jpa.builder;


import net.reduck.jpa.specification.builder.NativeQuerySqlBuilder;
import org.junit.Test;

/**
 * @author Reduck
 * @since 2022/9/23 17:59
 */
public class NativeQuerySqlBuilderTest {

    @Test
    public void testBuild() {
        NativeQuerySqlBuilder builder = new NativeQuerySqlBuilder();
        builder
                .select("username", "password", "create_time", "update_time")
                .from("user")
                .where("")
                .groupBy("username")
                .orderBy("id")
                .limit(3)
        ;

        System.out.println(builder.build());
    }
}
