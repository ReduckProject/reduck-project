package net.reduck.chat.server.pojo;

import lombok.Data;
import net.reduck.chat.server.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Reduck
 * @since 2023/1/10 02:57
 */
@Data
public class ChatHeader {
    private String endpoint;

    private MessageType messageType;

    private Map<String, String> header = new HashMap<>();

    private boolean body;

    private boolean attach;

    private String cookie;
}
