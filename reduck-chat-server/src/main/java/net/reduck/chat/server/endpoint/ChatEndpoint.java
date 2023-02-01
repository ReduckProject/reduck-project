package net.reduck.chat.server.endpoint;

import net.reduck.chat.server.annotation.Endpoint;
import net.reduck.chat.server.annotation.EndpointMapping;
import net.reduck.chat.server.pojo.ResponseVO;

/**
 * @author Reduck
 * @since 2023/1/9 16:39
 */
@Endpoint
@EndpointMapping(value = "/chat")
public class ChatEndpoint {

    @EndpointMapping(value = "/user")
    public ResponseVO<?> user() {
        return ResponseVO.ok();
    }

    @EndpointMapping(value = "/group")
    public ResponseVO<?> group() {
        return ResponseVO.ok();
    }
}
