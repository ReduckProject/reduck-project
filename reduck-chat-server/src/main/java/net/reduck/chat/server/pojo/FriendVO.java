package net.reduck.chat.server.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Reduck
 * @since 2023/1/9 16:26
 */
@Data
@Builder
public class FriendVO {

    private String username;

    private String nickname;

    private String userId;
}
