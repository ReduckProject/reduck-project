package net.reduck.jpa.test.json;

import lombok.Data;

/**
 * @author Gin
 * @since 2023/6/29 11:30
 */
@Data
public class J2 extends BaseJ{

    private String password;

    @Override
    public String getType() {
        return "j2";
    }
}
