package net.reduck.chat.server.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Reduck
 * @since 2023/1/9 14:17
 */
@Data
public class LoginVO {

    private String username;

    private List<InfoPair> friendList;

    private List<InfoPair> groupList;

}
