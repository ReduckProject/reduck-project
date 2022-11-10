package net.reduck.chat.server;

import java.net.Socket;

/**
 * @author Gin
 * @since 2022/11/9 20:48
 */
public class SessionProcessor implements Runnable {

    private final Socket socket;

    public SessionProcessor(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

    }
}
