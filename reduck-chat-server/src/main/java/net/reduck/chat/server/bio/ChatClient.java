package net.reduck.chat.server.bio;

import net.reduck.chat.server.io.ChatMessageReader;
import net.reduck.chat.server.io.ChatMessageWriter;
import net.reduck.chat.server.pojo.ChatMessage;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.utils.MessageBuilder;
import net.reduck.chat.server.utils.RequestSerializer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Reduck
 * @since 2023/1/4 11:32
 */
public class ChatClient {
    private final String ip;
    private final int port;

    private final String username;

    private final String password;

    private final String clientId;

    private Socket socket;

    private ChatMessageWriter writer;

    private ChatMessageReader reader;

    private Scanner scanner;

    public ChatClient(String ip, int port, String username, String password, String clientId) throws IOException {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        connect();
    }

    public ChatClient(String ip, int port, String username, String password, String clientId, Scanner scanner) throws IOException {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.scanner = scanner;
        connect();
    }

    private void connect() throws IOException {
        if(this.socket != null) {
            if(!socket.isClosed()) {
                socket.close();
            }
        }
        this.socket =  new Socket(ip, port);
        this.writer = new ChatMessageWriter(socket.getOutputStream());
        this.reader = new ChatMessageReader(socket.getInputStream());
        login();
        chat();
        Executors.execute(() -> {
            try {
                chatProcess();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void reconnect() throws IOException {
        connect();
    }

    private void login() throws IOException {

        ChatRequest<LoginTO> request = new ChatRequest<>();
        request.setEndpoint("/login");
        LoginTO to = new LoginTO();
        to.setClientId(clientId);
        to.setPassword(password);
        to.setUsername(username);
        request.setData(to);

        writer.writeMessage(request);
    }

    private void chat() {
        System.out.println("Process chat");
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
                    e.printStackTrace();
                }
            }

            System.out.println("Scanner end");
        });
    }

    private void chatProcess() throws IOException {
        String message;
        while ((message = new String(reader.readMessage())) != null) {
            try {
                ChatRequest<ChatMessage> chatRequest = RequestSerializer.read(message, ChatMessage.class);
                System.out.println(chatRequest.getData().getTo() + ":" + chatRequest.getData().getMessage());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
