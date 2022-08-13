package net.reduck.jpa.test.vo;

import lombok.Data;
import net.reduck.jpa.specification.OperatorType;
import net.reduck.jpa.specification.PageRequest;
import net.reduck.jpa.specification.annotation.SpecificationQuery;

/**
 * @author Reduck
 * @since 2022/8/10 14:52
 */
@Data
public class UserListTO extends PageRequest {
    @SpecificationQuery(compare = OperatorType.CONTAIN)
    private String username = "test";

    @SpecificationQuery(join = "info", property = "email", compare = OperatorType.CONTAIN)
    private String email = "test";
}
