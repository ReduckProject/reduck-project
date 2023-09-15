package net.reduck.jpa.test.vo;

import lombok.Data;
import net.reduck.jpa.entity.transformer.TimeDescTransformer;
import net.reduck.jpa.specification.annotation.ColumnProjection;

/**
 * @author Gin
 * @since 2023/9/14 09:55
 */
@Data
public class UserListVO {

    @ColumnProjection(name = "username")
    private String user;

    private String password;

    @ColumnProjection(name = "createTime", transformer = TimeDescTransformer.class)
    private String createTimeDesc;

    @ColumnProjection(join = "info")
    private String phoneNo;
}
