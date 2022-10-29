package net.reduck.jpa.test.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.reduck.data.protection.core.ProtectionPassword;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author Reduck
 * @since 2022/8/6 23:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String username;

    @ProtectionPassword
    private String password;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private boolean deleted;

    /**
     * 版本
     */
    @Version
    @Column(name = "version")
    private long version;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private long createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private long updateTime;

    @Id
    @GeneratedValue
    private Long id;

    public UserVO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
