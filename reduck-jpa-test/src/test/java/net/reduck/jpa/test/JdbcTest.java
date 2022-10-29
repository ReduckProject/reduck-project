package net.reduck.jpa.test;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Properties;

/**
 * @author Reduck
 * @since 2022/9/22 17:14
 */
public class JdbcTest {
    private final String jdbcUrl = "jdbc:mysql://localhost:3306/jpa_test?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false&serverTimezone=GMT%2B8";
    private final String username = "root";
    private final String password = "111111";

    @Test
    public void testConnect() throws SQLException {

        Connection connection = getDriver().connect(jdbcUrl, config());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        int count = 0;
        ResultSetMetaData metaData = resultSet.getMetaData();
        String[] columns = new String[metaData.getColumnCount()];
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.print(metaData.getCatalogName(i) + " | ");

            System.out.print(metaData.getColumnClassName(i) + " | ");
            System.out.print(metaData.getColumnDisplaySize(i) + " | ");
            System.out.print(metaData.getColumnLabel(i) + " | ");
            System.out.print(metaData.getColumnName(i) + " | ");
            System.out.print(metaData.getColumnType(i) + " | ");
            System.out.println(metaData.getColumnTypeName(i));
        }
        while (resultSet.next()) {
//            System.out.println(resultSet.getCursorName());
            count++;
        }
        System.out.println(count);
        System.out.println();
    }

    private Driver getDriver() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            return DriverManager.getDriver(jdbcUrl);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties config() {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        return properties;
    }
}
