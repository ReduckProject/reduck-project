package net.reduck.chat.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Reduck
 * @since 2023/1/4 11:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    private String to;

    private String message;
}
