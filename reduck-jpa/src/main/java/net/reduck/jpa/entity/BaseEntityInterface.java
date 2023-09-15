package net.reduck.jpa.entity;

/**
 * @author Reduck
 */

public interface BaseEntityInterface {

    Long getId();

    Long getCreateTime();

    Long setCreateTime(Long createTime);

    Long getUpdateTime();

    Long setUpdateTime(Long updateTime);

    boolean isDeleted();


}
