package net.reduck.chat.server.pojo;

import lombok.Builder;
import lombok.Data;
import net.reduck.chat.server.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Reduck
 * @since 2023/1/10 17:26
 */
@Data
public class MessageHeader {

    private String endpoint;

    private MessageType messageType;

    private Map<String, String> header = new HashMap<>();

    private boolean body;

    private boolean attach;

    private String cookie;


}
