package net.reduck.chat.server.bio;

import net.reduck.chat.server.io.ChatMessageReader;
import net.reduck.chat.server.io.ChatMessageWriter;
import net.reduck.chat.server.pojo.ChatMessage;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.utils.MessageBuilder;
import net.reduck.chat.server.utils.RequestSerializer;
import net.reduck.validator.Validators;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Reduck
 * @since 2022/11/9 20:32
 */
public class ChatServer {
    private String cryptoKeys = "1234567812345678";

    private Map<String, Socket> socketsHolder = new ConcurrentHashMap<>();

    public void listen(int port) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);

        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();

            Executors.execute(() -> {
                        String code = "";
                        try {
                            code = authenticate(socket);
                            chatProcess(code, socket);
                        } catch (IOException e) {
                            socketsHolder.remove(code);
                            throw new RuntimeException(e);
                        }

                        socketsHolder.remove(code);
                    }
            );
        }
    }

    private String authenticate(Socket socket) throws IOException {
        ChatMessageReader reader = new ChatMessageReader(socket.getInputStream());
        String message = new String(reader.readMessage());

        ChatRequest<LoginTO> chatRequest = RequestSerializer.read(message, LoginTO.class);
        if (!Validators.validate(chatRequest).isEmpty()) {
            socket.close();
            throw new RuntimeException("Params check error");
        }
        System.out.println(chatRequest.getData().toString());

        socketsHolder.put(chatRequest.getData().getUsername(), socket);

        return chatRequest.getData().getUsername();
    }

    private void chatProcess(String username, Socket socket) throws IOException {
        ChatMessageReader reader = new ChatMessageReader(socket.getInputStream());
        String message;
        while ((message = new String(reader.readMessage())) != null) {
            System.out.println("Receive message: " + message);
            ChatRequest<ChatMessage> chatRequest = RequestSerializer.read(message, ChatMessage.class);

            Socket toSocket = socketsHolder.get(chatRequest.getData().getTo());
            if (toSocket == null) {
                System.out.println(chatRequest.getData().getTo() + " not online");
            } else {
                ChatMessageWriter writer = new ChatMessageWriter(toSocket.getOutputStream());
                writer.writeMessage(MessageBuilder.chat(username, chatRequest.getData().getMessage()));
            }
        }
    }
}
