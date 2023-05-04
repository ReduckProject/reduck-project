package net.reduck.chat.server.utils;

import net.reduck.chat.server.pojo.ChatMessage;
import net.reduck.chat.server.pojo.ChatRequest;

/**
 * @author Reduck
 * @since 2023/1/4 11:13
 */
public class MessageBuilder {

    public static ChatRequest<ChatMessage> chat(String to, String message) {
        ChatRequest<ChatMessage> request = new ChatRequest<>();
        request.setEndpoint("/chat");
        request.setData(new ChatMessage(to, message));
        return request;
    }
}
