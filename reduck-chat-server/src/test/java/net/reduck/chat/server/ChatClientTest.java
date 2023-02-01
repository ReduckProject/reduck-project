package net.reduck.chat.server;

import net.reduck.chat.server.bio.ChatClient;
import net.reduck.chat.server.bio.ChatServer;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * @author Reduck
 * @since 2022/11/22 10:26
 */
public class ChatClientTest {

    @Test
    public void testClient() throws IOException {
        ChatClient client = new ChatClient("127.0.0.1", 3879, "Reducksco","123456", UUID.randomUUID().toString());
    }

    @Test
    public void testServer() throws IOException, InterruptedException {
        new ChatServer().listen(3879);
    }
}
