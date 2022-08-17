package net.reduck.jpa.specification;

import lombok.SneakyThrows;
import net.reduck.jpa.entity.BaseEntityInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * @author Reduck
 * @since 2019/8/19 17:03
 */
@SuppressWarnings("all")
public class JpaRepositoryExtendedImpl<T extends BaseEntityInterface, ID> extends SimpleJpaRepository implements JpaRepositoryExtend {
    private static final int BATCH_SIZE = 20000;
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

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
        CriteriaQuery cq = em.getCriteriaBuilder().createTupleQuery();
        Root root = cq.from(getDomainClass());

        Map<String, PropertyDescriptor> propertyDescriptorMap = SpecificationAnnotationIntrospector.getPropertyDescriptor(returnType);
        List<String> names = new ArrayList<>(propertyDescriptorMap.keySet());
        List<Selection> selections = names.stream().map(name -> root.get(name)).collect(Collectors.toList());
        cq.multiselect(selections);



        Predicate predicate = new SpecificationResolver(o, getDomainClass()).toPredicate(root, cq, em.getCriteriaBuilder());
        if(predicate != null){
            cq.where(predicate);
        }
        List<Tuple> result = em.createQuery(cq).getResultList();

        List tranferReult = new ArrayList();
        if(!CollectionUtils.isEmpty(result)){
            for(Tuple tuple : result){
                Object target = returnType.newInstance();
                for(int i = 0; i< names.size(); i++){
                    ReflectionUtils.invokeMethod(propertyDescriptorMap.get(names.get(i)).getWriteMethod(), target, tuple.get(i));
                }
                tranferReult.add(target);
            }
        }

        return tranferReult;
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
}
