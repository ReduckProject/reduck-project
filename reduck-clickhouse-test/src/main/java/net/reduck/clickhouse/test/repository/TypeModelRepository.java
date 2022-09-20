package net.reduck.clickhouse.test.repository;

import net.reduck.clickhouse.test.entity.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Reduck
 * @since 2022/9/17 15:46
 */
public interface TypeModelRepository extends JpaRepository<TypeModel, Long> {
}
