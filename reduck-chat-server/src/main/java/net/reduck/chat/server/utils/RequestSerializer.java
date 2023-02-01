package net.reduck.chat.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.validator.JsonUtils;

import java.io.IOException;

/**
 * @author Reduck
 * @since 2023/1/3 17:50
 */
public class RequestSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> ChatRequest<T> read(String data, Class<T> type) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructParametricType(ChatRequest.class, type));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ChatRequest<T> read(byte[] data, Class<T> type) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructParametricType(ChatRequest.class, type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] write(ChatRequest request) {
        try {
            return mapper.writeValueAsBytes(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
