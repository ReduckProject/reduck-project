package net.reduck.jpa.specification;

import net.reduck.jpa.entity.BaseEntityInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 官方文档地址：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
 * @author Reduck
 * @since 2019/8/19 16:59
 */
@NoRepositoryBean
public interface JpaRepositoryExtend<T extends BaseEntityInterface, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Optional<T> findByIdAndDeletedFalse(ID id);

    /**
     * 根据集合查询允许集合为空
     *
     * @param ids
     * @return
     */
    List<T> findAllWithIdsNullable(Iterable<ID> ids);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    T getOneByDeletedFalse(ID id);

    /**
     * 字段映射查询，不分页
     *
     * @param o
     * @return
     */
    List<T> findAllNoPageWith(Object o);

    /**
     * 字段映射查询，不分页
     * 并转换为自定义类型
     *
     * @param o 查询参数
     * @param transfer 转换器
     * @param <R> 返回元素类型
     * @return
     */
    <R> List<R> findAllNoPageWith(Object o, Function<T, R> transfer);

    <R> List<R> findAllNoPageWith(Object o, Class<R> returnType);

    /**
     * 分页查询
     *
     * @param pageRequest
     * @param <X>
     * @return
     */
    <X extends PageRequest> Page<T> findAllWith(X pageRequest);

    /**
     * 分页查询并转换为指定类型
     *
     * @param query
     * @param transfer
     * @param <X>
     * @param <R>
     * @return
     */
    <X extends PageRequest, R> PaginationResult<R> findAllWith(X query, Function<T, R> transfer);


    <X extends PageRequest, R> PaginationResult<R> findAllWith(X query, Class<R> returnType);

    /**
     * 批量插入
     *
     * @param var1
     * @param <S>
     * @return
     */
    <S extends T> Iterable<S> batchInsert(Iterable<S> var1);

    /**
     * 持久化
     *
     * @param entity
     * @param <S>
     * @return
     */
    <S extends T> S persist(S entity);

    /**
     * 批量持久化
     *
     * @param entities
     * @param <S>
     * @return
     */
    <S extends T> Iterable<S> persistAll(Iterable<S> entities);

    <T> List<T> findAllByBuilder(SpecificationQueryBuilder builder);

    <R> List<R> executeNativeSql(String sql, Class<R> returnType);
}
