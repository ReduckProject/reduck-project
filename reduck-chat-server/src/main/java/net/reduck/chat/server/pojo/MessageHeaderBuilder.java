package net.reduck.chat.server.pojo;

import net.reduck.chat.server.enums.MessageType;

import java.util.Map;

public class MessageHeaderBuilder {
    private MessageHeader instance = new MessageHeader();

    public MessageHeaderBuilder endpoint(String endpoint) {
        instance.setEndpoint(endpoint);
        return this;
    }

    public MessageHeaderBuilder messageType(MessageType messageType) {
        instance.setMessageType(messageType);
        return this;
    }

    public MessageHeaderBuilder header(Map<String, String> header) {
        instance.setHeader(header);
        return this;
    }

    public MessageHeaderBuilder body(boolean body) {
        instance.setBody(body);
        return this;
    }

    public MessageHeaderBuilder attach(boolean attach) {
        instance.setAttach(attach);
        return this;
    }

    public MessageHeaderBuilder cookie(String cookie) {
        instance.setCookie(cookie);
        return this;
    }

    public MessageHeader build() {
        return this.instance;
    }
}