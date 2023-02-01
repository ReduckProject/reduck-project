package net.reduck.chat.server;

import net.reduck.chat.server.bio.ChatServerHandler;
import net.reduck.chat.server.environment.ApplicationEnvironment;

import java.io.IOException;

/**
 * @author Reduck
 * @since 2023/1/10 02:47
 */
public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException, InstantiationException, IllegalAccessException {
        ApplicationEnvironment.init();
        new ChatServerHandler().listen(3879);
    }
}
