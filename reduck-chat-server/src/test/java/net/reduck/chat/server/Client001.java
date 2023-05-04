package net.reduck.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Reduck
 * @since 2022/12/30 10:57
 */
public class Client001 {

    public static void main(String[] args) throws IOException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Socket socket = new Socket("127.0.0.1", 3879);
        socket.getOutputStream().write("001\n".getBytes());
        socket.getOutputStream().flush();

        Scanner scanner = new Scanner(System.in);
        String send = "";
        executorService.execute(() -> {
            String tmp;
            while (scanner.hasNextLine()) {
                String text = "002:" + scanner.next() + "\n";
                try {
                    socket.getOutputStream().write(text.getBytes());
                    socket.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        executorService.execute(() -> {
            String tmp = "";
            while (true) {
                try {
                    if ((tmp = reader.readLine()) != null) {
                        System.out.println(tmp);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
