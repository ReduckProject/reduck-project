package net.reduck.jpa.test.vo;

import lombok.Data;
import net.reduck.jpa.specification.PageRequest;
import net.reduck.jpa.specification.annotation.AttributeProjection;
import net.reduck.jpa.specification.enums.CompareOperator;
import net.reduck.jpa.test.controller.Test2Controller;

import javax.validation.constraints.NotNull;

/**
 * @author Reduck
 * @since 2022/8/10 14:52
 */
@Data
public class UserListTO extends PageRequest {
    @AttributeProjection(compare = CompareOperator.CONTAINS, property = {"username", "password"})
    @NotNull.List({
            @NotNull(groups = Test2Controller.class, message = "SB"),
            @NotNull(groups = UserListTO.class, message = "NSB"),
    })
    @NotNull(groups = Test2Controller.class)
    private String username;

    @AttributeProjection(join = "info", property = "email", compare = CompareOperator.CONTAINS)
    private String email;
}
