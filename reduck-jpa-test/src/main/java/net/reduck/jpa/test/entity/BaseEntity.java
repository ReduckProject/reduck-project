package net.reduck.jpa.test.entity;

import net.reduck.jpa.entity.BaseEntityInterface;
import net.reduck.jpa.entity.BaseEntityListener;

import javax.persistence.*;

/**
 * @author Reduck
 * @since 2022/8/6 22:38
 */
@MappedSuperclass
@EntityListeners(value = {BaseEntityListener.class})
public abstract class BaseEntity implements BaseEntityInterface {


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

    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * 由子类实现主键
     *
     * @return
     */

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Long setCreateTime(Long createTime) {
        return null;
    }

    @Override
    public Long setUpdateTime(Long updateTime) {
        return null;
    }

}
