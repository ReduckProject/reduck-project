package net.reduck.jpa.specification;

import net.reduck.jpa.entity.BaseEntityInterface;
import net.reduck.jpa.entity.BaseEntityListener;

/**
 * @author Reduck
 * @see javax.persistence.PreUpdate
 * @see javax.persistence.PrePersist
 * @see javax.persistence.PostPersist
 * @see javax.persistence.PostUpdate
 * @see BaseEntityListener#registerPostPersistAction(EntityUpdatedAction)
 * @see BaseEntityListener#registerPostUpdateAction(EntityUpdatedAction)
 * @see BaseEntityListener#registerPostPersistAction(EntityUpdatedAction)
 * @see BaseEntityListener#registerPreUpdateAction(EntityUpdatedAction)
 *
 *         在实体更新或者插入时，执行自定义的响应动作
 * @since 2020/10/12 15:24
 */
public interface EntityUpdatedAction {
    /**
     * 执行响应动作
     *
     * @param entity
     */
    void doAction(BaseEntityInterface entity);

    /**
     * 是否满足执行的条件
     *
     * @param entity
     *
     * @return
     */
    boolean isExecuted(BaseEntityInterface entity);

    default void execute(BaseEntityInterface entity) {
        if (isExecuted(entity)) {
            doAction(entity);
        }
    }

}
