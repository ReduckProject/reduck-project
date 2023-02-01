package net.reduck.chat.server.io;

import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.utils.Ciphers;
import net.reduck.chat.server.utils.RequestSerializer;
import net.reduck.validator.JsonUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Reduck
 * @since 2023/1/4 11:08
 */
public class ChatMessageWriter {

    private final OutputStream os;
    private final MessageCipher cipher;
    private final byte[] end = new byte[] {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};

    public ChatMessageWriter(OutputStream os, byte[] keys) {
        this.os = os;
        this.cipher = new MessageCipher(keys);
    }

    public ChatMessageWriter(OutputStream os) {
        this.os = os;
        this.cipher = new MessageCipher("1234567812345678".getBytes());
    }

    public synchronized void writeMessage(ChatRequest<?> request ) throws IOException {
        os.write(cipher.encrypt(RequestSerializer.write(request)));
        os.write(end);
        os.flush();
    }
}
