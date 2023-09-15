package net.reduck.jpa.specification;

import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author Gin
 * @since 2023/5/8 11:50
 */

public class EntityInformationUtils {
    public static <T, ID extends Serializable> JpaMetamodelEntityInformation<T, ID> getEntityInformation(
            EntityManager entityManager, Class<T> entityClass) {
//        Class<T> entityType = entityManager.getMetamodel().entity(entityClass).getJavaType();
        return new JpaMetamodelEntityInformation<>(entityClass, entityManager.getMetamodel());
    }
}