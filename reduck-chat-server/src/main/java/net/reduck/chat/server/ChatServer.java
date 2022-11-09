package net.reduck.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Gin
 * @since 2022/11/9 20:32
 */
public class ChatServer {


    public void listen() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(3879);
        Socket socket = serverSocket.accept();

        while (!socket.isClosed()){

            socket.getOutputStream().write("hello \n".getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());
            Thread.sleep(5000);
        }
    }
}
