package net.reduck.jpa.test.vo;

import lombok.Data;
import net.reduck.jpa.specification.OperatorType;
import net.reduck.jpa.specification.PageRequest;
import net.reduck.jpa.specification.annotation.SpecificationQuery;
import net.reduck.jpa.test.controller.Test2Controller;

import javax.validation.constraints.NotNull;

/**
 * @author Reduck
 * @since 2022/8/10 14:52
 */
@Data
public class UserListTO extends PageRequest {
    @SpecificationQuery(compare = OperatorType.CONTAIN)
    @NotNull.List({
            @NotNull(groups = Test2Controller.class, message = "SB"),
            @NotNull(groups = UserListTO.class, message = "NSB"),
    })
    @NotNull(groups = Test2Controller.class)
    private String username;

    @SpecificationQuery(join = "info", property = "email", compare = OperatorType.CONTAIN)
    private String email = "test";
}
