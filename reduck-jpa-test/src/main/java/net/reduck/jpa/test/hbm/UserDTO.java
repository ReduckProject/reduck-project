package net.reduck.jpa.test.hbm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Reduck
 * @since 2022/9/21 10:55
 */
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Long info_id;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private boolean is_deleted;

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
    private long create_time;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private long update_time;

    @Id
    @GeneratedValue
    private Long id;

}
