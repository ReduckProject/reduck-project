package net.reduck.chat.server.io;

import net.reduck.chat.server.pojo.ChatMessage;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.utils.RequestSerializer;
import net.reduck.validator.JsonUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Reduck
 * @since 2023/1/4 09:53
 */
public class ChatMessageReader {

    private final MessageCipher cipher;

    private final MessageReader reader;

    public ChatMessageReader(InputStream in) {
        this.reader = new MessageReader(in, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        this.cipher = new MessageCipher("1234567812345678".getBytes());
    }

    public ChatMessageReader(InputStream in, byte[] keys) {
        this.reader = new MessageReader(in, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        this.cipher = new MessageCipher(keys);
    }

    public synchronized byte[] readMessage() throws IOException {
        return cipher.decrypt(reader.readMessage());
    }

    public synchronized <T> ChatRequest<T> readMessage(Class<T> messageType) throws IOException {
        return RequestSerializer.read(readMessage(), messageType);
    }
}
