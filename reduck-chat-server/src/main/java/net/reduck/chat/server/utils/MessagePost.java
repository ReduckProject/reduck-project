package net.reduck.chat.server.utils;

import net.reduck.chat.server.pojo.MessageHeader;
import net.reduck.chat.server.pojo.MessageHeaderBuilder;
import net.reduck.chat.server.pojo.MessageRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Reduck
 * @since 2023/1/12 16:48
 */
public class MessagePost {

    private final String ip;

    private final int port;

    private Socket socket;

    private final MessageRequestSerializer serializer = new MessageRequestSerializer();

    public MessagePost(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;

        connect();
    }

    private synchronized void connect() throws IOException {
        if (socket != null && socket.isClosed()) {
            socket.close();
        }
        socket = new Socket(ip, port);
    }

    public void post(String endpoint) {
        MessageHeader header = new MessageHeaderBuilder()
                .endpoint(endpoint)
                .build();

        MessageRequest<?> request = new MessageRequest<>(header, null, null);


    }

    public void post(String endpoint, Object body) {
    }

    private synchronized void write(MessageRequest<?> request) throws IOException {
        MessageHeader header = request.getHeader();
        writeSign(MessageSign.HEADER_START);
        writeData(serializer.serializeHeader(header));
        writeSign(MessageSign.HEADER_END);

        if (header.isBody()) {
            writeSign(MessageSign.BODY_START);
            writeData(serializer.serializeHeader(request.getBody()));
            writeSign(MessageSign.BODY_END);
        }

        if (header.isAttach()) {
            writeSign(MessageSign.BODY_START);
            writeData(request.getAttach());
            writeSign(MessageSign.BODY_END);
        }


    }

    private synchronized void writeData(InputStream in) throws IOException {
        int read = -1;
        while ((read = Streams.read(in, writeBuffer)) != -1) {
            socket.getOutputStream().write(writeBuffer, 0, read);
            ;
        }
    }

    private synchronized void writeSign(byte[] sign) throws IOException {
        socket.getOutputStream().write(sign);
    }

    private final byte[] writeBuffer = new byte[4 * 1024];

}
