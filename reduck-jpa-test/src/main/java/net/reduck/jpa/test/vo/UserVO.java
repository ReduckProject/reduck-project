package net.reduck.jpa.test.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Reduck
 * @since 2022/8/6 23:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private String username;

    private String password;
}
