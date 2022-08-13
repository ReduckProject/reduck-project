package net.reduck.jpa.specification;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;

/**
 * @author Reduck
 * @since 2019/8/20 18:03
 */
public class JpaRepositoryFactoryExtended extends JpaRepositoryFactory {
    public JpaRepositoryFactoryExtended(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return JpaRepositoryExtendedImpl.class;
    }

}
