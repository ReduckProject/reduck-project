package net.reduck.netools;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;

/**
 * @author Gin
 * @since 2023/4/14 10:00
 */
public class LoggerLevelTest {

    @BeforeClass
    public static void test() {
        Logger logger = (Logger) LoggerFactory.getLogger("root");
        logger.setLevel(Level.INFO);
    }

    @Test
    public void test2() {
        new JScpClient(new HostInfo("172.16.44.181", 22,"root", "1"))
                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/mvnw.cmd", "/opt");

//        new JScpClient("172.16.44.181", "root", "1")
//                .upload("/Users/zhanjinkai/Downloads/dbf", "/opt");
    }
}
