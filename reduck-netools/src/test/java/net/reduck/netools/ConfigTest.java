package net.reduck.netools;

import org.junit.Test;

import java.io.File;

/**
 * @author Gin
 * @since 2023/4/13 20:25
 */
public class ConfigTest {

    @Test
    public void test() {
        // 获取配置文件路径
        String configFilePath = ConfigTest.class.getClassLoader().getResource("application.properties").getFile();
        File configFile = new File(configFilePath);

        // 输出配置文件路径
        System.out.println("配置文件路径：" + configFile.getAbsolutePath());
    }

    @Test
    public void test2() {
        new JScpClient(new HostInfo("172.16.44.181", 22,"root", "1"))
                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/mvnw.cmd", "/opt");

//        new JScpClient("172.16.44.181", "root", "1")
//                .upload("/Users/zhanjinkai/Downloads/dbf", "/opt");
    }
}
