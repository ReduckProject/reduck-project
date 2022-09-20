package net.reduck.clickhouse.test.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * @author Reduck
 * @since 2022/9/17 15:51
 */
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ClickhouseSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {

    public ClickhouseSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public ClickhouseSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }
}
