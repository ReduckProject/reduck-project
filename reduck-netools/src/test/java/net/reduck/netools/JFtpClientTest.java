package net.reduck.netools;

import org.junit.Test;

import java.io.IOException;

/**
 * @author Gin
 * @since 2023/4/14 15:47
 */
public class JFtpClientTest {

    @Test
    public void test() throws IOException {
        new JFtpClient(new HostInfo("124.221.119.11", 21, "ginsco", "111111"))
                .connect()
                .login()
                .upload("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/test/java/net/reduck/netools/AlertTest.java"
                        , "/")
//                .list("/tmp/zhanjinkai")
                .logout()
                .disconnect();
    }
}
