package net.reduck.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Reduck
 * @since 2022/10/27 10:04
 */
@Data
public class TestUser {

    @NotNull
    private String username;

    @NotEmpty
    private String password;
}
