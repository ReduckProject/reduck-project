package net.reduck.chat.server.bio;

import net.reduck.chat.server.handler.EndpointHandler;
import net.reduck.chat.server.io.ChatMessageReader;
import net.reduck.chat.server.io.ChatMessageWriter;
import net.reduck.chat.server.pojo.ChatMessage;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.utils.MessageBuilder;
import net.reduck.chat.server.utils.RequestSerializer;
import net.reduck.validator.Validators;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Reduck
 * @since 2022/11/9 20:32
 */
public class ChatServerHandler {
    private String cryptoKeys = "1234567812345678";

    private Map<String, Socket> socketsHolder = new ConcurrentHashMap<>();
    private Map<String, ChatMessageReader> readerMap = new HashMap<>();

    public void listen(int port) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);
        EndpointHandler endpointHandler = new EndpointHandler();

        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();

            Executors.execute(() -> {
                        String code = "";
                        try {
                            ChatMessageReader reader = new ChatMessageReader(socket.getInputStream());
                            endpointHandler.handle(messageRead(socket, reader));
                        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                            socketsHolder.remove(code);
                            throw new RuntimeException(e);
                        }

                        socketsHolder.remove(code);
                    }
            );
        }
    }

    public ChatRequest<String> messageRead(Socket socket, ChatMessageReader reader) throws IOException {
        ChatRequest<String> request = reader.readMessage(String.class);
        return request;
    }
}
