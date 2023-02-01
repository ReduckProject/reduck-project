package net.reduck.chat.server;

import net.reduck.chat.server.bio.Executors;
import net.reduck.chat.server.io.ChatMessageWriter;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.utils.Ciphers;
import net.reduck.chat.server.utils.MessageBuilder;
import net.reduck.validator.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * @author Reduck
 * @since 2022/12/30 10:57
 */
public class Client007 {
    private static byte[] keys = "1234567812345678".getBytes();

    private static byte[] end = new byte[] {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};

    public static void main(String[] args) throws IOException, InterruptedException {


        Socket socket = new Socket("127.0.0.1", 3879);
        ChatMessageWriter writer = new ChatMessageWriter(socket.getOutputStream(), keys);

        ChatRequest<LoginTO> request = new ChatRequest<>();
        request.setEndpoint("/login");
        LoginTO to = new LoginTO();
        to.setClientId(UUID.randomUUID().toString());
        to.setPassword("Test");
        to.setUsername("Reducksco");
        request.setData(to);

        writer.writeMessage(request);


        Scanner scanner = new Scanner(System.in);
        Executors.execute(() -> {
            String tmp;
            while (scanner.hasNextLine()) {
                try {
                    tmp = scanner.nextLine();
                    String[] data = tmp.split(":");
                    if(data.length != 2) {
                        continue;
                    }
                    writer.writeMessage(MessageBuilder.chat(data[0], data[1]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Executors.execute(() -> {
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
