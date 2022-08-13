package net.reduck.jpa.entity;

import net.reduck.jpa.specification.EntityUpdatedAction;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 */
@Component
public class BaseEntityListener {
    private static final List<EntityUpdatedAction> prePersistActions = new ArrayList<>();
    private static final List<EntityUpdatedAction> preUpdateActions = new ArrayList<>();
    private static final List<EntityUpdatedAction> postPersistActions = new ArrayList<>();
    private static final List<EntityUpdatedAction> postUpdateActions = new ArrayList<>();

    @PrePersist
    public void prePersist(BaseEntityInterface entity) {
        if (entity.getCreateTime() == 0) {
            entity.setCreateTime(System.currentTimeMillis());
        }

        if (entity.getUpdateTime() == 0) {
            entity.setUpdateTime(entity.getCreateTime());
        }

        for (EntityUpdatedAction action : prePersistActions) {
            action.execute(entity);
        }
    }

    @PreUpdate
    public void preUpdate(BaseEntityInterface entity) {
        entity.setUpdateTime(System.currentTimeMillis());

        for (EntityUpdatedAction action : preUpdateActions) {
            action.execute(entity);
        }
    }

    @PostPersist
    public void postPersist(BaseEntityInterface entity) {
        for (EntityUpdatedAction action : postPersistActions) {
            action.execute(entity);
        }
    }

    @PostUpdate
    public void postUpdate(BaseEntityInterface entity) {
        for (EntityUpdatedAction action : postUpdateActions) {
            action.execute(entity);
        }
    }

    public static void registerPrePersistAction(EntityUpdatedAction action) {
        prePersistActions.add(action);
    }

    public static void registerPreUpdateAction(EntityUpdatedAction action) {
        preUpdateActions.add(action);
    }

    public static void registerPostPersistAction(EntityUpdatedAction action) {
        postPersistActions.add(action);
    }

    public static void registerPostUpdateAction(EntityUpdatedAction action) {
        postUpdateActions.add(action);
    }
}
