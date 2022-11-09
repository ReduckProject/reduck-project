package net.reduck.chat.server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Gin
 * @since 2022/11/9 20:38
 */
public class ChatTest {

    @Test
    public void test() throws IOException, InterruptedException {
        new ChatServer().listen();
    }


    @Test
    public void test2() throws IOException {
        Socket socket = new Socket("127.0.0.1", 3879);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String tmp;
        while ((tmp = reader.readLine()) != null){
            System.out.println(tmp);

            socket.getOutputStream().write("hello \n".getBytes());
        }
    }
}
