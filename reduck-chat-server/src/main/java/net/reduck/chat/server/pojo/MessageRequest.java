package net.reduck.chat.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

/**
 * @author Reduck
 * @since 2023/1/10 17:28
 */
@Data
@AllArgsConstructor
public class MessageRequest<B> {

    private MessageHeader header;

    private B body;

    private InputStream attach;
}
