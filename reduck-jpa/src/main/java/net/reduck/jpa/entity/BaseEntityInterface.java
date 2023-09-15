package net.reduck.jpa.entity;

/**
 * @author Reduck
 */

public interface BaseEntityInterface {

    long getId();

    long getCreateTime();

    void setCreateTime(long createTime);

    long getUpdateTime();

    void setUpdateTime(long updateTime);

    boolean isDeleted();


}
