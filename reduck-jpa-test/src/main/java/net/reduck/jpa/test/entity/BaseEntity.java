package net.reduck.jpa.test.entity;

import lombok.Getter;
import lombok.Setter;
import net.reduck.jpa.entity.BaseEntityInterface;
import net.reduck.jpa.entity.BaseEntityListener;

import javax.persistence.*;

/**
 * @author Reduck
 * @since 2022/8/6 22:38
 */
@MappedSuperclass
@EntityListeners(value = {BaseEntityListener.class})
@Getter
@Setter
public abstract class BaseEntity implements BaseEntityInterface {

    @Id
    @GeneratedValue
    private long id;

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

}
