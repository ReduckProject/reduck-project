package net.reduck.chat.server.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Reduck
 * @since 2023/1/3 17:48
 */
@Data
public class LoginTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String clientId;
}
