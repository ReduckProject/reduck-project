package net.reduck.jpa;

import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.event.internal.CallbacksFactory;
import org.hibernate.jpa.event.spi.CallbackRegistry;
import org.hibernate.jpa.event.spi.CallbackType;
import org.hibernate.metamodel.internal.MetamodelImpl;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Reduck
 * @since 2023/5/22 1:26
 */
@Repository
@Transactional
public class BatchRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MetamodelImpl metamodel;
    private final CallbackRegistry callbackRegistry;

    public BatchRepository(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean, EntityManagerFactory factory, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        this.callbackRegistry = CallbacksFactory.buildCallbackRegistry((SessionFactoryImpl) localContainerEntityManagerFactoryBean.getNativeEntityManagerFactory());
        this.callbackRegistry = new CallbackRegistry() {
            @Override
            public boolean hasRegisteredCallbacks(Class entityClass, CallbackType callbackType) {
                return false;
            }

            @Override
            public void preCreate(Object entity) {

            }

            @Override
            public void postCreate(Object entity) {

            }

            @Override
            public boolean preUpdate(Object entity) {
                return false;
            }

            @Override
            public void postUpdate(Object entity) {

            }

            @Override
            public void preRemove(Object entity) {

            }

            @Override
            public void postRemove(Object entity) {

            }

            @Override
            public boolean postLoad(Object entity) {
                return false;
            }

            @Override
            public boolean hasPostCreateCallbacks(Class entityClass) {
                return false;
            }

            @Override
            public boolean hasPostUpdateCallbacks(Class entityClass) {
                return false;
            }

            @Override
            public boolean hasPostRemoveCallbacks(Class entityClass) {
                return false;
            }

            @Override
            public boolean hasRegisteredCallbacks(Class entityClass, Class annotationClass) {
                return false;
            }
        };
        this.metamodel = ((MetamodelImpl) factory.getMetamodel());
    }


    public <T> void insert(List<T> entities, Class<T> entityType) {
        int batchSize = 3000;

        if (entities == null || entities.size() == 0) {
            return;
        }

        // 获取持久化类
        AbstractEntityPersister persister = ((AbstractEntityPersister) metamodel.locateEntityPersister(entityType.getName()));
        // 获取IDENTITY类型insertSQL
        String insertSQL = persister.getSQLIdentityInsertString();

        if (insertSQL == null) {
            throw new RuntimeException("Just support id generate by IDENTITY");
        }

        String valuesTemplate = insertSQL.substring(insertSQL.lastIndexOf("("));
        StringBuilder stringBuilder = new StringBuilder(insertSQL);

        System.out.println("Total insert " + entities.size());
        for (int i = 0; i < entities.size(); i += batchSize) {
            List<T> batch = entities.subList(i, Math.min(i + batchSize, entities.size()));
            if (batch.size() == 0) {
                continue;
            }

            List<Object> params = new ArrayList<>();
            boolean first = true;
            for (T entity : batch) {
                // 参数预处理
                callbackRegistry.preCreate(entity);
                if (!first) {
                    stringBuilder.append(",").append(valuesTemplate);
                }
                first = false;
                // 参数填充
                params.addAll(Arrays.asList(persister.getPropertyValuesToInsert(entity, null, null)));
            }
            jdbcTemplate.update(stringBuilder.toString(), params.toArray());
            System.out.println("Finished insert " + i);
        }
    }
}
