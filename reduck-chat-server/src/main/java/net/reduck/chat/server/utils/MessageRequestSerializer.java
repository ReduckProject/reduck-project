package net.reduck.chat.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.reduck.chat.server.pojo.MessageRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Reduck
 * @since 2023/1/12 17:06
 */
public class MessageRequestSerializer {
    private final ObjectMapper mapper = new ObjectMapper();

    private final int buffer = 1024 * 4;

//    public InputStream serialize(MessageRequest<?> request) throws IOException {
//        MessageHeader header = request.getHeader();
//
//        ByteArrayOutputStream os = new ByteArrayOutputStream(buffer);
//        os.write(mapper.writeValueAsBytes(header));
//        if (header.isBody()) {
//            os.write(mapper.writeValueAsBytes(request.getBody()));
//        }
//
//        if (header.isAttach()) {
//            return new UnionInputStream(new ByteArrayInputStream(os.toByteArray()), request.getAttach());
//        }
//
//        return new UnionInputStream(new ByteArrayInputStream(os.toByteArray()));
//    }

    public InputStream serializeHeader(Object data) throws IOException {
        return new ByteArrayInputStream(mapper.writeValueAsBytes(data));
    }
}
