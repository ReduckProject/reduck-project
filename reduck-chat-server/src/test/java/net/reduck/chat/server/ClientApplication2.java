package net.reduck.chat.server;

import net.reduck.chat.server.bio.ChatClient;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Reduck
 * @since 2023/1/4 13:01
 */
public class ClientApplication2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatClient client = new ChatClient("127.0.0.1", 3879, "Reduck","123456", UUID.randomUUID().toString(), new Scanner(System.in));
        Thread.sleep(1000000000);
    }
}
