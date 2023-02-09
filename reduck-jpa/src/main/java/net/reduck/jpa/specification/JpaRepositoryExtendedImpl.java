package net.reduck.jpa.specification;

import lombok.SneakyThrows;
import net.reduck.jpa.entity.BaseEntityInterface;
import net.reduck.jpa.specification.transform.TupleToBeanResultTransformer;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * @author Reduck
 * @since 2019/8/19 17:03
 */
@SuppressWarnings("all")
public class JpaRepositoryExtendedImpl<T extends BaseEntityInterface, ID> extends SimpleJpaRepository implements JpaRepositoryExtend, ApplicationContextAware {
    private static final int BATCH_SIZE = 20000;
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    @Autowired
    private DataSource dataSource;

    public JpaRepositoryExtendedImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    @Override
    public Page findAllWith(PageRequest pageRequest) {
        return findAll(new SpecificationResolver(pageRequest, getDomainClass()), pageRequest.toPageable());
    }

    @Override
    public PaginationResult findAllWith(PageRequest query, Function transfer) {
        PaginationResult paginationResult = new PaginationResult().of(
                findAll(new SpecificationResolver(query, getDomainClass()), query.toPageable())
                , transfer
        );

        return paginationResult;
    }

    @Override
    public PaginationResult findAllWith(PageRequest query, Class returnType) {

        return null;
    }

    @Override
    public List findAllWithIdsNullable(Iterable iterable) {
        return iterable == null ? new ArrayList() : findAllById(iterable);
    }

    @Override
    public List findAllNoPageWith(Object o) {
        return findAll(new SpecificationResolver(o, getDomainClass()), Pageable.unpaged()).getContent();
    }

    @Override
    @Transactional
    public Iterable batchInsert(Iterable var1) {
        Iterator iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            em.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            em.flush();
            em.clear();
        }
        return var1;
    }

    @Override
    public List findAllNoPageWith(Object o, Function transfer) {
        return (List) findAllNoPageWith(o).stream().map(transfer).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public List findAllNoPageWith(Object o, Class returnType) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(returnType);
        Root root = cq.from(getDomainClass());
        cq.multiselect(Arrays.stream(returnType.getDeclaredFields()).map(field -> root.get(field.getName())).collect(Collectors.toList()));
        Predicate predicate = new SpecificationResolver(o, getDomainClass()).toPredicate(root, cq, em.getCriteriaBuilder());
        if (predicate != null) {
            cq.where(predicate);
        }
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Optional<T> findByIdAndDeletedFalse(Object id) {
        TypedQuery<T> query = getQuery(new SpecificationResolver(new DeletedFalse(false, id), getDomainClass()), getDomainClass(), Pageable.unpaged());
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public T getOneByDeletedFalse(Object id) {
        Optional<T> entity = findByIdAndDeletedFalse(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            return null;
        }
    }

    @Override
    public BaseEntityInterface persist(BaseEntityInterface entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Iterable persistAll(Iterable entities) {
        List list = new ArrayList();
        for (Object baseEntity : entities) {
            list.add(persist((BaseEntityInterface) baseEntity));
        }
        return list;
    }

    @Override
    public List findAllByBuilder(SpecificationQueryBuilder builder) {
        return findAll(builder.build(getDomainClass()));
    }

    @Override
    public List executeNativeSql(String sql, Class returnType) {
//        em.createQuery(sql).getResultList();
        Query query = em.createNativeQuery(sql);

        if (!(query instanceof NativeQueryImpl)) {
            throw new UnsupportedOperationException();
        }

        return ((NativeQueryImpl) query)
                .setResultTransformer(new TupleToBeanResultTransformer<>(returnType))
                .getResultList();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.dataSource = applicationContext.getBean(DataSource.class);
    }

    class DeletedFalse {
        public DeletedFalse(boolean deleted, Object id) {
            this.deleted = deleted;
            this.id = id;

        }

        private boolean deleted;

        private Object id;

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }
    }

    protected <V, S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec,Class<S> domainClass, Class<V> resultType, Sort sort) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
        query.multiselect(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return em.createQuery(query);
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }
}
