package net.reduck.jpa.specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Gin
 * @since 2023/2/8 10:10
 */
public class JpaExecutor {
    private final EntityManager em;

    public JpaExecutor(EntityManager em) {
        this.em = em;
    }

    public <T> void execute(PredicateInfo info, Class<T> domainType) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainType);
        Root<T> root = query.from(domainType);
        query.select(root);
    }
}
