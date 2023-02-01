package net.reduck.chat.server;

import net.reduck.chat.server.bio.ChatServer;

import java.io.IOException;

/**
 * @author Reduck
 * @since 2023/1/4 11:06
 */
public class ServerApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        new ChatServer().listen(3879);
    }
}
